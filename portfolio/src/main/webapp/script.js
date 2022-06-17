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

let formVisibility;


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
    await fetch('/chat?' + new URLSearchParams({
        text: msg,
    }), {method : "POST"})
    //add recent text to the wall
    addText(msg);
    
}
// delete every text on chat wall and rewrite
async function refreshChatWall(){
    let response = await fetch('/chat')
    const textResponse = await response.json();
    deleteWall();
    for(let i = textResponse.length - 1; i >= 0; i--) {
        addText(textResponse[i]["text"]);
    }
    
}

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

function addText(msg){
    let e = document.createElement('p');
    e.innerText = msg;
    document.getElementById("chat-wall-text-area").appendChild(e);
}
// delete every text on chat wall
function deleteWall(){
    
    let e = document.getElementById("chat-wall-text-area");
    //e.firstElementChild can be used.
    var child = e.lastElementChild; 
    while (child) {
        e.removeChild(child);
        child = e.lastElementChild;
    }
}

function init() {
    // hide email form
    formVisibility = false;
    let form = document.getElementById("form-container");
    form.style.display = "none";
    // open default tab
    document.getElementById("defaultOpen").click();
    // refresh chat wall
    refreshChatWall();
}


init();