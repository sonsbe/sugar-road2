<template>
<div class="app-body">
    <div class = "content">

        <a href="javascript:history.back();" id="backBtn">◀</a>
        <br><br><br>
        <h4 id="title">프로필 변경</h4>
        <br>
            <br>
        <br>
           <img v-bind:src="InfoImg" id="userImage">

            <label class="imgChange" for="input-image">
                <img src="src/assets/mypage/img/edit.png">
            </label>
            <form id="editForm" @submit.prevent="userInfoEdit" enctype="multipart/form-data">
                <input type="hidden" name="userId" value="InfoId">
                <input type="file" name="image" style="display:none" id="input-image" onchange="changeProfile(this)">
                <h5 class="editText">PW</h5>
                <br>
                <input class="editBox" type="password" name="userPassword" required v-bind:value="Password" ><br>
                <h5 class="editText">이름</h5>
                <br>
                <input class="editBox" type="text" name="userName" v-bind:value="InfoName" ><br>
                <h5 class="editText">별명</h5>
                <br>
                <input class="editBox" type="text" name="nickname" v-bind:value="InfoNickname" ><br>
                <h5 class="editText">email</h5>
                <br>
                <input class="editBox" type="text" name="userEmail" v-bind:value="InfoEmail"><br>
                <br>
                <input id="editButtom" type="submit" value="업데이트 프로필"/>
            </form>
        
            <h5 id="iframed" th:text="${msg}"></h5>
        <div id="delete"><a href="/delete" style="text-decoration: none">회원탈퇴</a></div>

    </div>
</div>
</template>

<script setup>
import { onMounted, ref } from "vue";
import axios from "axios";

const InfoImg = ref('');
const InfoId = ref('');
const Password = ref('');
const InfoName = ref('');
const InfoNickname = ref('');
const InfoEmail = ref('');

const PutId = ref('');
const PutPassword = ref('');
const PutName = ref('');
const PutNickname = ref('');
const PutEmail = ref('');

  onMounted( () => {
    axios.get("http://localhost:1023/mypage/테스터ID")
          .then(response => {
              console.log(response);
              return response.data;
          })
          .then(user => {
              console.log(user);
              InfoId.value = `${user.userId}`
              InfoImg.value = `${user.userImagePath}`;
              InfoId.value = `${user.id}`;
              InfoName.value = `${user.userName}`;
              InfoNickname.value = `${user.nickname}`;
              InfoEmail.value = `${user.userEmail}`;
            })
            .catch(err => console.error(err));
  })

  function userInfoEdit(){
    console.log("put request")
    console.log(InfoId)
    console.log(InfoId.value)
    axios.put("http://localhost:1023/mypage/테스터ID", {
        id: InfoId.value,
        userPassword: Password.value,
        userImagePath: InfoImg.value,
        userName: InfoName.value,
        nickname: InfoNickname.value,
        userEmail: InfoEmail.value
    },{
        headers: {
            "Content-Type": "application/json",
        },
    })
        .then((response) => {
            console.log("put response", response);
            window.alert(response.data);
        })
        .catch(response => {
            console.log("put error", error);
            window.alert(response.data);
        })
  }

</script>

<style scoped>
@import "src/assets/mypage/edit.css";
</style>