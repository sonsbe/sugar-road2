let commentInput = document.querySelector("#commentInput");
let referenceTypeSave;
let referenceIdSave;
let focusComment;
let commentSave;
let loader = document.querySelector("#loader");
const sleep = new Promise((resolve, reject) => {
    setTimeout(() => {
      resolve("foo");
    }, 2000);
});

commentInput.requestOptions = {};
commentInput.requestOptions.body = {};

document.querySelector(".content").classList.add("comment-content");

class Comment extends HTMLDivElement{
    constructor(){
        super();
        this.referenceType = this.getAttribute("data-referenceType");
        this.referenceId = this.getAttribute("data-referenceId");
        this.loginId = this.getAttribute("data-loginId");

        referenceTypeSave = this.referenceType;
        referenceIdSave = this.referenceId;

        console.log(`this.referenceType : ${this.referenceType} this.referenceId : ${this.referenceId} this.loginId : ${this.loginId}`)

        // 무한 스크롤 관련 부분
        this.iterator = 0;
        this.distance = 8;
        commentSave = this;
        document.querySelector(".content").addEventListener("scrollend", function(){
            toggleLoader();
            sleep.then(commentSave.render()
                    .then(() => {
                        toggleLoader();
                    })
                    .catch((err)=>{
                        console.log(err);
                        toggleLoader();
                    })
            );
        });

        this.render();
    }

    render(){
        let nextIterator = document.querySelectorAll(".v-table").length;
        return fetch(`/comment/${this.referenceType}?id=${this.referenceId}&startPoint=${this.iterator}&count=${this.distance}`)
        .then(async (response) => {
            if (!response.ok) throw await response.json();
            return response.json();
        })
        .then(json => this.readJson(json))
        .then(() => {(document.querySelectorAll(".v-table")[nextIterator]).focus()});
    }
    //진짜 난잡한 코드 댓글 구조 처음부터 다시짜고 제대로 다시 만들것
    renderBeforeAll(){
        this.innerHTML = "";
        let lastIter = this.iterator + 1;
        this.iterator = 0;

        fetch(`/comment/${this.referenceType}?id=${this.referenceId}&startPoint=${0}&count=${lastIter}`)
        .then(async (response) => {
            if (!response.ok) throw await response.json();
            return response.json();
        })
        .then(json => this.readJson(json));
    }

    readJson(json){
        let comments = Object.values(json);
        this.iterator += comments.length;
        comments.forEach(
            (comment) => {
                console.log(comment);

                this.innerHTML += `

                    <table class="v-table ${comment["parentComment"]?"child-comment":"comment"} ${comment["userId"] === this.loginId?"pink1":""}" id = "c${comment[this.referenceType + "CommentId"]}">
                      <tr>
                          <td class = "v-table-half t5">
                                ${comment["nickname"]}
                          </td>
                          <td class = ".v-table-eight">
                          </td>
                          <td class = ".v-table-eight">
                          </td>
                          <td class = "v-table-quater t6 right">
                                ${comment["postedDate"].substring(0,10)}
                          </td>
                      </tr>
                      <tr>
                          <td colspan="4" class = "t6 v-item" id = "c${comment[this.referenceType + "CommentId"] + "-content"}">${comment["content"]}</td>
                      </tr>
                      <tr>
                          <td>
                            <button is = "custom-recommendation" class = "no-border" style = "background-color : transparent;" data-referenceType=${this.referenceType === 'post'?"C":"M"} data-referenceId="${comment[this.referenceType + "CommentId"]}"></button>
                          </td>
                          <td class = "t6">
                                ${comment["parentComment"]?"":"<a onclick = 'setParent("+ comment[this.referenceType + "CommentId"] +");' class = 't-lightgray'>답글</a>"}
                          </td>
                          <td class = "t6">
                                ${comment["userId"] === this.loginId?"<a onclick = 'editComment(" + comment[this.referenceType + "CommentId"] + ");' class = 't-lightgray'>수정</a>":""}
                          </td>
                          <td class = "t6">
                                ${comment["userId"] === this.loginId?"<a onclick = 'deleteComment(" + comment[this.referenceType + "CommentId"] + ");' class = 't-lightgray'>삭제</a>":""}
                          </td>
                      </tr>
                  </table>

                `;
            }
        );
    }
}

customElements.define("custom-comment", Comment, {extends:"div"});

function checkFocus(id){
    return focusComment == document.querySelector("#c" + id);
}

function colorFocus(id){
    (focusComment != undefined)&&(focusComment.classList.remove("pink2"));
    if(checkFocus(id)){
        focusComment = undefined;
        return true
    }
    focusComment = document.querySelector("#c" + id);
    focusComment.classList.add("pink2");
    return false
}

function setParent(id){
    console.log(commentInput.requestOptions.body["parentComment"]);
    if (!colorFocus(id)){
        commentInput.requestOptions.body.parentComment = id;
        commentInput.focus();
    }
    else {
        delete commentInput.requestOptions.body.parentComment;
    }
}

function editComment(id){
    if (!colorFocus(id)){
        let content = document.querySelector(`#c${id}-content`);
        commentInput.value = content.textContent;
        commentInput.requestOptions.body[`${referenceTypeSave}CommentId`] = id;
        commentInput.focus();
    }
    else {
        commentInput.setAttribute("value", "");
        delete commentInput.requestOptions.body[`${referenceTypeSave}CommentId`];
    }
}

function deleteComment(id){
    fetch(`/comment/${referenceTypeSave}/delete?id=${id}`)
    .then(() => {
        document.querySelector("#comment-area").renderBeforeAll();
    });
}

function writeComment(){
    commentInput.requestOptions.headers = new Headers({"Content-Type" : "application/json"});
    commentInput.requestOptions.method = "POST";
    commentInput.requestOptions.body[`${referenceTypeSave}Id`] = referenceIdSave;
    commentInput.requestOptions.body.content = commentInput.value;
    let commentId = commentInput.requestOptions.body[referenceTypeSave + "CommentId"];
    let api = `/comment/${referenceTypeSave}/${commentInput.requestOptions.body["parentComment"]?"child/":""}${commentId?"edit":"write"}`
    if(commentId){
        document.querySelector(`#c${commentId}-content`).textContent = commentInput.value;
    }
    console.log(commentInput.requestOptions);

    commentInput.requestOptions.body = JSON.stringify(commentInput.requestOptions.body);

    fetch(api, commentInput.requestOptions)
    .then(() => {
        document.querySelector("#comment-area").renderBeforeAll();
        commentInput.value = "";
        commentInput.requestOptions = {};
        commentInput.requestOptions.body = {};
    });

}


// 답글이면 색깔 바꾸고 값 변경하는 기능

// 수정 삭제 버튼 누르면 작동하는 기능

// 전송 보내면 comment 영역 다시 render 
