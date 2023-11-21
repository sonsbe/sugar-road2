let postList = document.querySelector(".post-list");
let nextIterator = document.querySelectorAll(".post-thumbnail").length;
let distance = 4;

const sleep = new Promise((resolve, reject) => {
    setTimeout(() => {
      resolve("foo");
    }, 2000);
});

function readJson(json){
    let posts= Object.values(json);
    nextIterator += posts.length;
    posts.forEach(
        (post) => {
            console.log(post);

            postList.innerHTML += `

            <div class="post-thumbnail">
            <a href="/post/detail?id=${post.postId}">
                <table class="v-table">
                    <tr>
                        <td>
                            <p class="t5 bold">${post.title}</p>
                        </td>
                        <td class="v-table-half right">
                            <p class="t6">댓글수: ${post.commentCount} 추천수:${post.recommendCount}</p>
                        </td>

                    </tr>
                    <tr class="center">
                        <td colspan="2">
                            <img src="${post.postImage.length?post.postImage[0]:'/images/icons/sugarroad.png'}" alt="img">
                        </td>
                    </tr>
                    <tr>
                        <td colspan="2" class="left">
                            <span class="t5 bold">${post.userId}</span>
                            <span class="t5" th:text="${post.content.length>20?post.content.substring(0, 19):post.content}"></span>
                        </td>
                    </tr>
                </table>
            </a>
            </div>

            `;
        }
    );
}

function render(){
    return fetch(`/post/get?startPoint=${nextIterator}&count=${distance}`)
    .then(async (response) => {
        if (!response.ok) throw await response.json();
        return response.json();
    })
    .then(json => readJson(json))
    .then(() => {document.querySelectorAll(".post-thumbnail")[nextIterator].focus()});
}

document.querySelector(".content").addEventListener("scrollend", function(){
    toggleLoader();
    sleep.then(render()
            .then(() => {
                toggleLoader();
            })
            .catch((err)=>{
                console.log(err);
                toggleLoader();
            })
    );
});

