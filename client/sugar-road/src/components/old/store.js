let storeList = document.querySelector(".storeList");
let nextIterator = document.querySelectorAll(".storeBox").length;
let distance = 4;

const sleep = new Promise((resolve, reject) => {
    setTimeout(() => {
      resolve("foo");
    }, 2000);
});

function readJson(json){
    let stores= Object.values(json);
    nextIterator += stores.length;
    stores.forEach(
        (store) => {
            console.log(store);

            storeList.innerHTML += `

            <div class="storeBox">
                <a href="/store/detail?storeId=${store.storeId}" >
                <div class="storeBoxTop">
                        <div class="store-name"><b>${store.storeName}</b></div>
                        <div class="store-like">좋아요 수 : <span>5</span></div>
                </div>
                <img src="${store.storeImagePath}" class="storeImg" alt="가게 대표 이미지"></img>
                <div class="store-desc">${store.storeDesc}</div>
                </a>
            <br/>
            </div>

            `;
        }
    );
}

function render(){
    return fetch(`/store/get?startPoint=${nextIterator}&count=${distance}`)
    .then(async (response) => {
        if (!response.ok) throw await response.json();
        return response.json();
    })
    .then(json => readJson(json))
    .then(() => {document.querySelectorAll(".storeBox")[nextIterator].focus()});
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

