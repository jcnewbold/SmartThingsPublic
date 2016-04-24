/**
 *  Raspberry Pi
 *
 *  Copyright 2014 Nicholas Wilde
 *
 *  Monitor your Raspberry Pi using SmartThings and WebIOPi <https://code.google.com/p/webiopi/>
 *
 *  Companion WebIOPi python script can be found here:
 *  <https://github.com/nicholaswilde/smartthings/blob/master/device-types/raspberry-pi/raspberrypi.py>
 *
 *  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 *  in compliance with the License. You may obtain a copy of the License at:
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed
 *  on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License
 *  for the specific language governing permissions and limitations under the License.
 *
 */
 
import groovy.json.JsonSlurper

preferences {
        input("ip", "string", title:"IP Address", description: "192.168.1.150", required: true, displayDuringSetup: true)
        input("port", "string", title:"Port", description: "8000", defaultValue: 8000 , required: true, displayDuringSetup: true)
        input("username", "string", title:"Username", description: "webiopi", required: true, displayDuringSetup: true)
        input("password", "password", title:"Password", description: "Password", required: true, displayDuringSetup: true)
}

metadata {
	definition (name: "Raspberry Pi", namespace: "jcnewbold/smartthings", author: "Nicholas Wilde") {
		capability "Polling"
		capability "Refresh"
		capability "Temperature Measurement"
        capability "Sensor"
        capability "Actuator"
        //capability "Contact Sensor"
        
        attribute "cpuPercentage", "string"
        attribute "memory", "string"
        attribute "diskUsage", "string"
        attribute "contact", "string"
        
        command "restart"
        
	}

	simulator {
		// TODO: define status and reply messages here
	}

	tiles {
		valueTile("temperature", "device.temperature", width: 1, height: 1) {
            state "temperature", label:'${currentValue}° CPU', unit: "F",
            backgroundColors:[
                [value: 25, color: "#153591"],
                [value: 35, color: "#1e9cbb"],
                [value: 47, color: "#90d2a7"],
                [value: 59, color: "#44b621"],
                [value: 67, color: "#f1d801"],
                [value: 76, color: "#d04e00"],
                [value: 77, color: "#bc2323"]
            ]
        }
        standardTile("button", "device.switch", width: 1, height: 1, canChangeIcon: true) {
			state "off", label: 'Off', icon: "st.Electronics.electronics18", backgroundColor: "#ffffff", nextState: "on"
			state "on", label: 'On', icon: "st.Electronics.electronics18", backgroundColor: "#79b821", nextState: "off"
		}
        valueTile("cpuPercentage", "device.cpuPercentage", inactiveLabel: false) {
        	state "default", label:'${currentValue}% CPU', unit:"Percentage",
            backgroundColors:[
                [value: 31, color: "#153591"],
                [value: 44, color: "#1e9cbb"],
                [value: 59, color: "#90d2a7"],
                [value: 74, color: "#44b621"],
                [value: 84, color: "#f1d801"],
                [value: 95, color: "#d04e00"],
                [value: 96, color: "#bc2323"]
            ]
        }
        valueTile("memory", "device.memory", width: 1, height: 1) {
        	state "default", label:'${currentValue} MB', unit:"MB",
            backgroundColors:[
                [value: 353, color: "#153591"],
                [value: 287, color: "#1e9cbb"],
                [value: 210, color: "#90d2a7"],
                [value: 133, color: "#44b621"],
                [value: 82, color: "#f1d801"],
                [value: 26, color: "#d04e00"],
                [value: 20, color: "#bc2323"]
            ]
        }
        valueTile("diskUsage", "device.diskUsage", width: 1, height: 1) {
        	state "default", label:'${currentValue}% Disk', unit:"Percent",
            backgroundColors:[
                [value: 31, color: "#153591"],
                [value: 44, color: "#1e9cbb"],
                [value: 59, color: "#90d2a7"],
                [value: 74, color: "#44b621"],
                [value: 84, color: "#f1d801"],
                [value: 95, color: "#d04e00"],
                [value: 96, color: "#bc2323"]
            ]
        }
        standardTile("contact", "device.contact", width: 1, height: 1) {
			state("closed", label:'${name}', icon:"st.contact.contact.closed", backgroundColor:"#79b821", action: "open")
			state("open", label:'${name}', icon:"st.contact.contact.open", backgroundColor:"#ffa81e", action: "close")
		}
        standardTile("contact1", "device.contact1", width: 1, height: 1) {
			state("closed", label:'${name}12', icon:"st.contact.contact.closed", backgroundColor:"#79b821")
			state("open", label:'${name}12', icon:"st.contact.contact.open", backgroundColor:"#ffa81e")
		}
        standardTile("restart", "device.restart", inactiveLabel: false, decoration: "flat") {
        	state "default", action:"restart", label: "Restart", displayName: "Restart"
        }
        standardTile("refresh", "device.refresh", inactiveLabel: false, decoration: "flat") {
        	state "default", action:"refresh.refresh", icon: "st.secondary.refresh"
        }
        main "button"
        details(["button", "temperature", "cpuPercentage", "memory" , "diskUsage", "contact", "contact1", "restart", "refresh"])
    }
}

// ------------------------------------------------------------------

// parse events into attributes
def parse(String description) {
    log.debug "description: ${description}"
    def map = [:]
    def descMap = parseDescriptionAsMap(description)
    log.debug descMap
    def headers = new String(descMap["headers"].decodeBase64())
    log.debug "Header: ${headers}"
    def body = new String(descMap["body"].decodeBase64())
    log.debug "body: ${body}"
    def slurper = new JsonSlurper()
    def result = slurper.parseText(body)
    log.debug "result: ${result}"
	if (result){
    	log.debug "Computer is up"
   		sendEvent(name: "switch", value: "on")
    }
    else
    {
        log.debug "Computer is off"
   		sendEvent(name: "switch", value: "off")
    }
    
    
    if (result.containsKey("cpu_temp")) {
        log.debug "Computer Temp ${result.cpu_temp}" 
    	sendEvent(name: "temperature", value: result.cpu_temp)
    }
    
    if (result.containsKey("cpu_perc")) {
        sendEvent(name: "cpuPercentage", value: result.cpu_perc)
    }
    
    if (result.containsKey("mem_avail")) {
    	log.debug "mem_avail: ${result.mem_avail}"
        sendEvent(name: "memory", value: result.mem_avail)
    }
    if (result.containsKey("disk_usage")) {
    	log.debug "disk_usage: ${result.disk_usage}"
        sendEvent(name: "diskUsage", value: result.disk_usage)
    }
  
    if (result.containsKey("gpio_value_17")) {
    	log.debug "gpio_value_17: ${result.gpio_value_17.toDouble().round()}"
        if (result.gpio_value_17.contains("0")){
        	log.debug "gpio_value_17: open"
            sendEvent(name: "contact", value: "open")
        } else {
        	log.debug "gpio_value_17: closed"
           sendEvent(name: "contact", value: "closed")
        }
    }
    if (result.containsKey("gpio_value_18")) {
    	log.debug "gpio_value_18: ${result.gpio_value_18.toDouble().round()}"
        if (result.gpio_value_18.contains("0")){
        	log.debug "gpio_value_18: open"
            sendEvent(name: "contact1", value: "open")
        } else {
        	log.debug "gpio_value_18: closed"
           sendEvent(name: "contact1", value: "closed")
        }
    }
}

// handle commands
def poll() {
	log.debug "Executing 'poll'"
    getRPiData()
}

def refresh() {
	log.debug "Executing 'refresh'"
    getRPiData()
    
}

def restart(){
    def uri = "/macros/getData"
    postAction2(uri)
    log.debug "Setting Max as network ID"
    device.deviceNetworkId = "4CEB42074248"
    //def uri = "/macros/reboot"
    //postAction(uri)
}

// Get CPU percentage reading
private getRPiData() {
	def uri = "/macros/getData"
    postAction(uri)
}

// ------------------------------------------------------------------

def someCommand() {
    subscribeAction("/path/of/event")
}

private subscribeAction(path, callbackPath="") {
    log.debug "subscribe($path, $callbackPath)"
    def address = getCallBackAddress()
    def ip = getHostAddress()
    
    log.debug "CallbackIP ${ip}" 
    log.debug "CallbackAddress ${address}" 
    log.debug "<http://${address}/notify$callbackPath>"

    def result = new physicalgraph.device.HubAction(
        method: "SUBSCRIBE",
        path: path,
        headers: [
            HOST: ip,
            CALLBACK: "<http://${address}/notify$callbackPath>",
            NT: "upnp:event",
            TIMEOUT: "Second-28800"
        ]
    )

    log.trace "SUBSCRIBE $path"

    result
}

private postAction2(path, callbackPath="") {
  log.debug "Setting IP and Port"
  setDeviceNetworkId(ip,port)  
 
  
  def userpass = encodeCredentials(username, password)
  
  def headers = getHeader(userpass)
  
  
    log.debug "subscribe($path, $callbackPath)"
    def address = getCallBackAddress()
    def ip = getHostAddress()
    
    log.debug "CallbackIP ${ip}" 
    log.debug "CallbackAddress ${address}" 
    log.debug "<http://${address}/notify$callbackPath>"

    def result = new physicalgraph.device.HubAction(
        method: "SUBSCRIBE",
        path: path,
        headers: [
            HOST: ip,
            CALLBACK: "<http://${address}/notify$callbackPath>",
            NT: "upnp:event",
            TIMEOUT: "Second-28800"
        ]
    )

    log.trace "SUBSCRIBE $path"

    result
}

private postAction(uri){
log.debug "Setting IP and Port"
  setDeviceNetworkId(ip,port) 
  log.debug "Setting Max as network ID"
  device.deviceNetworkId = "4CEB42074248";
  
  def userpass = encodeCredentials(username, password)
  
  def headers = getHeader(userpass)
  
  def hubAction = new physicalgraph.device.HubAction(
    method: "POST",
    path: uri,
    headers: headers
  )//,delayAction(1000), refresh()]
  log.debug("Executing hubAction on " + getHostAddress())
  //log.debug hubAction
  hubAction    
}

// ------------------------------------------------------------------
// Helper methods
// ------------------------------------------------------------------

def parseDescriptionAsMap(description) {
	description.split(",").inject([:]) { map, param ->
		def nameAndValue = param.split(":")
		map += [(nameAndValue[0].trim()):nameAndValue[1].trim()]
	}
}

private encodeCredentials(username, password){
	log.debug "Encoding credentials"
	def userpassascii = "${username}:${password}"
    def userpass = "Basic " + userpassascii.encodeAsBase64().toString()
    //log.debug "ASCII credentials are ${userpassascii}"
    //log.debug "Credentials are ${userpass}"
    return userpass
}

private getHeader(userpass){
	log.debug "Getting headers"
    def headers = [:]
    headers.put("HOST", getHostAddress())
    headers.put("Authorization", userpass)
    //log.debug "Headers are ${headers}"
    return headers
}

private delayAction(long time) {
	new physicalgraph.device.HubAction("delay $time")
}

private setDeviceNetworkId(ip,port){
  	def iphex = convertIPtoHex(ip)
  	def porthex = convertPortToHex(port)
  	device.deviceNetworkId = "$iphex:$porthex"
  	log.debug "Device Network Id set to ${iphex}:${porthex}"
}

// gets the address of the hub
private getCallBackAddress() {
    return device.hub.getDataValue("localIP") + ":" + device.hub.getDataValue("localSrvPortTCP")
}

private getHostAddress() {
	return "${ip}:${port}"
}

private String convertIPtoHex(ipAddress) { 
    String hex = ipAddress.tokenize( '.' ).collect {  String.format( '%02x', it.toInteger() ) }.join()
    return hex

}

private String convertPortToHex(port) {
	String hexport = port.toString().format( '%04x', port.toInteger() )
    return hexport
}