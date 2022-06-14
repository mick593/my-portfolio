// Copyright 2020 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     https://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

/**
 * Adds a random greeting to the page.
 */
function addRandomGreeting() {
    const greetings =
        ['Hello world!', 'Spy x Family is a great anime!', '你好，世界！', 'Bonjour le monde!', 'ดีจ้า'];
  
    // Pick a random greeting.
    const greeting = greetings[Math.floor(Math.random() * greetings.length)];
  
    // Add it to the page.
    const greetingContainer = document.getElementById('greeting-container');
    greetingContainer.innerText = greeting;
  }
  
    // mainly get the content from w3schools
function openTab(evt, tabName) {
    var i, tabcontent, tablinks;

    tabcontent = document.getElementsByClassName("tabcontent");
    for (i = 0; i < tabcontent.length; i++) {
        tabcontent[i].style.display = "none";
    }

    tablinks = document.getElementsByClassName("tablinks");
    for (i = 0; i < tablinks.length; i++) {
        tablinks[i].className = tablinks[i].className.replace(" active", "");
    }
    document.getElementById(tabName).style.display = "block";
    evt.currentTarget.className += " active";
}

async function showGreeting() {
    const response = await fetch('/hello');
    const textResponse = await response.text();

    const msgContainer = document.getElementById("message-box");
    msgContainer.innerText = textResponse;
}

async function randomGreeting() {
    const response = await fetch('/rand-hello');
    const textResponse = await response.json();
    let choose = Math.floor(Math.random() * textResponse.length);
    const msgContainer = document.getElementById("message-box");
    msgContainer.innerText = textResponse[choose];
}
// send text to server and update to the chat wall
function processTextInput() {
    let val = document.getElementById("chat-input").value;
    if(!val){
        console.log('no input');
    }else{
        handleSendText(val);
    }
  }
// helper function for sending text
async function handleSendText(msg) {
    console.log(msg);
    let response = await fetch('/chat?' + new URLSearchParams({
        text: msg,
    }), {method : "POST"})
    const textResponse = await response.json();
    console.log(textResponse);
    updateChatWall();
    
}
// delete every text on chat wall and rewrite
async function updateChatWall(){
    let response = await fetch('/chat')
    const textResponse = await response.json();
    deleteWall();
    textResponse.forEach(function (item, index) {
        addText(item);
      });
    
}
let formVisibility = false;
let form = document.getElementById("form-container");
form.style.display = "none";
function swapFormVisibility() {
    formVisibility = !formVisibility;
    // show form
    if(formVisibility) {
        let form = document.getElementById("form-container");
        form.style.display = "block";
    } else {    // hide
        let form = document.getElementById("form-container");
        form.style.display = "none";
    }
}
// update chat every 1 second
setInterval(updateChatWall, 1000);
document.getElementById("defaultOpen").click();
function addText(msg){
    let e = document.createElement('p');
    e.innerText = msg;
    document.getElementById("chat-wall").appendChild(e);
}
// delete every text on chat wall
function deleteWall(){
    
    let e = document.getElementById("chat-wall");
    //e.firstElementChild can be used.
    var child = e.lastElementChild; 
    while (child) {
        e.removeChild(child);
        child = e.lastElementChild;
    }
}