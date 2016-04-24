/**
 *  Virtual Contact Sensor
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
metadata {
	definition (name: "Virtual Contact Sensor", namespace: "jcnewbold", author: "Jason Newbold") {
		capability "Contact Sensor"
        
        command "openSensor"
        command "closeSensor"
	}

	simulator {
		// TODO: define status and reply messages here
	}

	tiles {
		standardTile("contact", "device.contact", width: 2, height: 2, canChangeIcon: true) {
            state "open", label: 'open', icon: "st.contact.contact.open", backgroundColor: "#79b821"
            state "closed", label: 'closed', icon: "st.contact.contact.closed", backgroundColor: "#ffffff"
        }
		
        main "contact"
        details(["contact"])
	}
}

// parse events into attributes
def parse(String description) {
	log.debug "Parsing '${description}'"
}

// handle commands
def openSensor() {
	log.debug "Virtual Device Open"
    sendEvent (name: "contact", value: "open", isStateChange:true)
}

def closeSensor() {
	log.debug "Virtual Device Closed"
    sendEvent (name: "contact", value: "closed", isStateChange:true)
}