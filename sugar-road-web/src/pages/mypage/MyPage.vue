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
                <a href="/InfoEdit" style="text-decoration: none">
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
                <a @click="logout()" style="text-decoration: none">로그아웃</a>
            </div>
        </div>
    </div>
</div>
</template>

<script setup>
import { onMounted, ref } from "vue";
import axios from "axios";
import router from "../../router";

const selectImg = ref('');
const selectNicname = ref('');
const selectEmail = ref('');

  onMounted( () => {
    if(sessionStorage.getItem("user") === undefined || sessionStorage.getItem("user") === null){
        console.log("if문 걸림", sessionStorage.getItem("user"));
        router.push("/login")
    } else {
        console.log("로그인 아이디", sessionStorage.getItem("user"));
    }
    const headerId = sessionStorage.getItem('user');
    axios.get(`http://localhost:1023/mypage/${headerId}`)
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
      
  const logout = () => {  
          sessionStorage.removeItem("token");
          sessionStorage.removeItem("user");
          window.alert("로그아웃 수행");
    }
  </script>

<style scoped>
@import "src/assets/mypage/index.css";
</style>