import Post from './post.js';
import Comment from './comment.js';

const buttonCommentsGET = document.getElementById("buttonCommentsGET");
const buttonCommentsPOST = document.getElementById("buttonCommentsPOST");
const buttonCommentsPUT = document.getElementById("buttonCommentsPUT");
const buttonCommentsDELETE = document.getElementById("buttonCommentsDELETE");

const buttonPostsGET = document.getElementById("buttonPostsGET");
const buttonPostsPOST = document.getElementById("buttonPostsPOST");
const buttonPostsPUT = document.getElementById("buttonPostsPUT");
const buttonPostsDELETE = document.getElementById("buttonPostsDELETE");

buttonCommentsGET.addEventListener('click', getComments);

function getComments(){
    fetch('http://localhost:3000/comments')
  .then((response) => response.json())
  .then((json) => {
    const divComments = document.getElementById("divComments");
    const ulComments = document.createElement("ul");
    divComments.appendChild(ulComments);
    for(let comment of json){
        const liComments = document.createElement("li");
        liComments.textContent = comment["id"] + " comme titre " + comment["text"];
        ulComments.appendChild(liComments);
    }
  });
}