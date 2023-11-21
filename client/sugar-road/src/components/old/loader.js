function toggleLoader(){
    document.querySelector("#loader").classList.toggle("display-none");
}

const onLoadTimer = new Promise((resolve, reject) => {
    document.querySelector("#page-loader").classList.toggle("display-none");
    document.querySelector("#page-loader").classList.toggle("fade-in");
    setTimeout(() => {
        document.querySelector("#page-loader").classList.toggle("display-none");
        resolve("foo");
    }, 1000);
});

addEventListener("DOMContentLoaded", (event) => {onLoadTimer.then(console.log("page-loader called!"));});
