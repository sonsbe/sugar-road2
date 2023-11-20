<template>
<div class="app-body">
    <div class = "content">
        <div class="top">
            <h4 id="title">MyPage</h4>

                <div class="profileBox">
                    <div class="imageDiv">
                        <img v-bind:src="selectImg" id="userImage">
                    </div>
                    <div class = "userInfo">
                    <h5>{{ selectNicname }}</h5>
                    <h6>{{ selectEmail }}</h6>
                    </div>
                </div>

                <h3 id="msg" th:if="${msg}" th:text="${msg}"></h3>
        </div>
        <div class="middle">
            <div class="menu">
                <a href="/mypage" style="text-decoration: none">
                    <div class="menuButton" id="button1">
                    <img src="src/assets/mypage/img/menuImg1.png" class="menuImg">
                        <h5 id="menuText1">프로필 수정</h5>
                    </div>
                </a>
                <a href="/mypage" style="text-decoration: none">
                    <div class="menuButton" id="button2">
                    <img src="src/assets/mypage/img/menuImg2.png" class="menuImg">
                        <h5 id="menuText2">내가 작성한 글</h5>
                    </div>
                </a>
                <a href="/mypage" style="text-decoration: none">
                    <div class="menuButton" id="button3">
                    <img src="src/assets/mypage/img/menuImg3.png" class="menuImg">
                        <h5 id="menuText3">내가 작성한 댓글</h5>
                    </div>
                </a>
                <a href="/mypage" style="text-decoration: none">
                    <div class="menuButton" id="button4">
                    <img src="src/assets/mypage/img/menuImg4.png" class="menuImg">
                        <h5 id="menuText4">좋아요 한 글</h5>
                    </div>
                </a>
            </div>
        </div>
        <div class="bottom">
            <div id="logOut">
                <a href="/logout" style="text-decoration: none">로그아웃</a>
            </div>
        </div>
    </div>
</div>
</template>

<script setup>
import { onMounted, ref } from "vue";
import axios from "axios";

const selectImg = ref('');
const selectNicname = ref('');
const selectEmail = ref('');

  onMounted( () => {
    axios.get("http://localhost:1023/mypage/테스터ID")
          .then(response => {
              console.log(response);
              return response.data;
          })
          .then(user => {
              console.log(user);
              selectImg.value = `${user.userImagePath}`;
              //selectImg.value = "src/assets/mypage/img/profileEx.png";
              selectNicname.value = `${user.nickname}`;
              selectEmail.value = `${user.userEmail}`;
            })
            .catch(err => console.error(err));
  })
      
  </script>

<style>
/* <!-- 상단 --> */

.top{

}

#title{

    display: flex;
    justify-content: center;
    padding-top: 10%;
    padding-bottom: 5%;
}

.profileBox{

}

.imageDiv{

    display: flex;
    justify-content: center;
}

#userImage{

    border-radius: 50%;
    border: 3px solid #D7CBE3;
    width: 20%;
    height: 20%;
    background-repeat: no-repeat;
    background-size: cover;
}

.userInfo{

    text-align: center;
    margin: 2%;
}

#msg{

}

/* <!-- 가운데 --> */

.middle{

    display: flex;
    justify-content: center;
}

.menu{
    margin: 10% auto;
    margin-left: 10%;
    margin-right: 10%;
    width: 70%;
}

.menuButton{
      display: flex;
      width: 100%;
      height: 60px;
      margin-bottom: 8%;
      border-radius: 15px;
      color: rgba(0, 0, 0, 0.70);
      align-items: center;

}

#button1{
  background: rgba(195, 220, 241, 0.6);
  backdrop-filter: blur(2px);
}

#button2{
  background: rgba(175, 150, 199, 0.6);
  backdrop-filter: blur(2px);
}

#button3{
  background: rgba(241, 171, 204, 0.6);
  border-radius: 15px;
}

#button4{
  background: rgba(247, 233, 237, 0.6);
  backdrop-filter: blur(2px);
}

.menuImg{
    width: 10%;
    margin-right: 10%;
    margin-left: 10%;
}

#menuText1{
    margin-left: 8%;
}

#menuText2{
    margin-left: 5%;
}

#menuText3{
    margin-left: 3%;
}

#menuText4{
    margin-left: 7%;
}


/* <!-- 하단 --> */

.bottom{

}

#logOut{
    text-align: center;
}
#logOut > a{
       color: gray;
       font-size: 14px;
}

</style>