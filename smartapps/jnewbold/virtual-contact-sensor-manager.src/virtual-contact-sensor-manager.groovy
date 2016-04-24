/**
 *  Virtual Contact Sensor Manager
 *
 *  Copyright 2016 Jason Newbold
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
definition(
    name: "Virtual Contact Sensor Manager",
    namespace: "jnewbold",
    author: "Jason Newbold",
    description: "descrption",
    category: "Convenience",
    iconUrl: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience.png",
    iconX2Url: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience@2x.png",
    iconX3Url: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience@2x.png")


preferences {
	section("Connect these virtual switches to the Arduino's relays") {
		input "contact1", title: "Switch for contact 1", "capability.contactSensor"
        input "contact2", title: "Switch for contact 2", "capability.contactSensor", required: false
        input "contact3", title: "Switch for contact 3", "capability.contactSensor", required: false
        input "contact4", title: "Switch for contact 4", "capability.contactSensor", required: false
        input "contact5", title: "Switch for contact 5", "capability.contactSensor", required: false
        input "contact6", title: "Switch for contact 6", "capability.contactSensor", required: false
        input "contact7", title: "Switch for contact 7", "capability.contactSensor", required: false
        input "contact8", title: "Switch for contact 8", "capability.contactSensor", required: false
	}
    section("Which Raspberry Pi MultiSensor to control?") {
		input "arduino", "device.raspberryPi"
    }    
}

def installed() {
	log.debug "Installed with settings: ${settings}"
	subscribe()
}

def updated() {
	log.debug "Updated with settings: ${settings}"
	unsubscribe()
	subscribe()
}


def subscribe() {

	// Listen to the virtual switches
	//subscribe(contact1, "switch.on", switchOn1)
	//subscribe(contact1, "switch.off", switchOff1)
    //subscribe(contact2, "switch.on", switchOn2)
	//subscribe(contact2, "switch.off", switchOff2)
    
    // Listen to anything which happens on the device
    subscribe(arduino, "contact1.open", sensorOpened1)
    subscribe(arduino, "contact1.closed", sensorClosed1)
    subscribe(arduino, "contact2.open", sensorOpened2)
    subscribe(arduino, "contact2.closed", sensorClosed2)
    subscribe(arduino, "contact3.open", sensorOpened3)
    subscribe(arduino, "contact3.closed", sensorClosed3)
    subscribe(arduino, "contact4.open", sensorOpened4)
    subscribe(arduino, "contact4.closed", sensorClosed4)
    subscribe(arduino, "contact5.open", sensorOpened5)
    subscribe(arduino, "contact5.closed", sensorClosed5)
    subscribe(arduino, "contact6.open", sensorOpened6)
    subscribe(arduino, "contact6.closed", sensorClosed6)
    subscribe(arduino, "contact7.open", sensorOpened7)
    subscribe(arduino, "contact7.closed", sensorClosed7)
    subscribe(arduino, "contact8.open", sensorOpened8)
    subscribe(arduino, "contact8.closed", sensorClosed8)
}

def switchOn1(evt)
{
	log.debug "switchOn1($evt.name: $evt.value: $evt.deviceId)"
    //if (arduino.currentValue("relay1") != "on")
    //{
   // 	log.debug "Sending RelayOn1 event to Arduino"
   // 	arduino.RelayOn1()
    //}
}

def switchOff1(evt)
{
    log.debug "switchOff1($evt.name: $evt.value: $evt.deviceId)"
   // if (arduino.currentValue("relay1") != "off")
   // {
   //     log.debug "Sending RelayOff1 event to Arduino"
   // 	arduino.RelayOff1()
   // }
}

def sensorOpened1(evt)
{
	log.debug "Sensor 1 was opened"
    if (contact1.currentValue("contact") != "open")
    {
    	log.debug "Flipping virtual contact sensor 1 open"
    	contact1.openSensor()
    } 	
}


def sensorClosed1(evt)
{
	log.debug "Sensor 1 was closed"
	if (contact1.currentValue("contact") != "closed")
	{
    	log.debug "Flipping virtual contact sensor 1 closed"
    	contact1.closeSensor()
    }
}

def sensorOpened2(evt)
{
	log.debug "Sensor 2 was opened"
    if (contact2.currentValue("contact") != "open")
    {
    	log.debug "Flipping virtual contact sensor 2 open"
    	contact2.openSensor()
    } 	
}


def sensorClosed2(evt)
{
	log.debug "Sensor 2 was closed"
	if (contact2.currentValue("contact") != "closed")
	{
    	log.debug "Flipping virtual contact sensor 2 closed"
    	contact2.closeSensor()
    }
}

def sensorOpened3(evt)
{
	log.debug "Sensor 3 was opened"
    if (contact3.currentValue("contact") != "open")
    {
    	log.debug "Flipping virtual contact sensor 3 open"
    	contact3.openSensor()
    } 	
}


def sensorClosed3(evt)
{
	log.debug "Sensor 3 was closed"
	if (contact3.currentValue("contact") != "closed")
	{
    	log.debug "Flipping virtual contact sensor 3 closed"
    	contact3.closeSensor()
    }
}

def sensorOpened4(evt)
{
	log.debug "Sensor 4 was opened"
    if (contact4.currentValue("contact") != "open")
    {
    	log.debug "Flipping virtual contact sensor 4 open"
    	contact4.openSensor()
    } 	
}


def sensorClosed4(evt)
{
	log.debug "Sensor 4 was closed"
	if (contact4.currentValue("contact") != "closed")
	{
    	log.debug "Flipping virtual contact sensor 4 closed"
    	contact4.closeSensor()
    }
}

def sensorOpened5(evt)
{
	log.debug "Sensor 5 was opened"
    if (contact5.currentValue("contact") != "open")
    {
    	log.debug "Flipping virtual contact sensor 5 open"
    	contact5.openSensor()
    } 	
}


def sensorClosed5(evt)
{
	log.debug "Sensor 5 was closed"
	if (contact5.currentValue("contact") != "closed")
	{
    	log.debug "Flipping virtual contact sensor 5 closed"
    	contact5.closeSensor()
    }
}

def sensorOpened6(evt)
{
	log.debug "Sensor 6 was opened"
    if (contact6.currentValue("contact") != "open")
    {
    	log.debug "Flipping virtual contact sensor 6 open"
    	contact6.openSensor()
    } 	
}


def sensorClosed6(evt)
{
	log.debug "Sensor 6 was closed"
	if (contact6.currentValue("contact") != "closed")
	{
    	log.debug "Flipping virtual contact sensor 6 closed"
    	contact6.closeSensor()
    }
}

def sensorOpened7(evt)
{
	log.debug "Sensor 7 was opened"
    if (contact7.currentValue("contact") != "open")
    {
    	log.debug "Flipping virtual contact sensor 7 open"
    	contact7.openSensor()
    } 	
}


def sensorClosed7(evt)
{
	log.debug "Sensor 7 was closed"
	if (contact7.currentValue("contact") != "closed")
	{
    	log.debug "Flipping virtual contact sensor 7 closed"
    	contact7.closeSensor()
    }
}

def sensorOpened8(evt)
{
	log.debug "Sensor 8 was opened"
    if (contact8.currentValue("contact") != "open")
    {
    	log.debug "Flipping virtual contact sensor 8 open"
    	contact8.openSensor()
    } 	
}


def sensorClosed8(evt)
{
	log.debug "Sensor 8 was closed"
	if (contact8.currentValue("contact") != "closed")
	{
    	log.debug "Flipping virtual contact sensor 8 closed"
    	contact8.closeSensor()
    }
}



