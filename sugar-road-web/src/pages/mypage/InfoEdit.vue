<template>
  <div class="not-overflow-y">
  <div class="app-body">
    <div class="content">
        <a href="javascript:history.back();" id="backBtn">◀</a>
        <br><br>
        <h4 id="title">프로필 변경</h4>
        <br><br><br>
           <img :src="InfoImg" id="userImage">

            <label class="imgChange" for="input-image">
                <img src="src/assets/mypage/img/edit.png">
            </label>
            <form id="editForm">
                <input type="hidden" name="userId" v-model="InfoId">
                <input type="file" name="image" style="display:none" id="input-image" @change="changeProfile">
                <h5 class="editText">PW</h5>
                <br>
                <input class="editBox" type="password" name="userPassword" v-model="Password" required><br>
                <h5 class="editText">이름</h5>
                <br>
                <input class="editBox" type="text" name="userName" v-model="InfoName"><br>
                <h5 class="editText">별명</h5>
                <br>
                <input class="editBox" type="text" name="nickname" v-model="InfoNickname"><br>
                <h5 class="editText">email</h5>
                <br>
                <input class="editBox" type="text" name="userEmail" v-model="InfoEmail"><br>
                <br>
                
            </form>
            <button id="editButtom" @click="userInfoEdit">업데이트 프로필</button>
      <div id="delete">
        <a @click="userDelete" style="text-decoration: none">회원탈퇴</a>
      </div>
    </div>
  </div>
  </div>
</template>

<script setup>
import { onMounted, ref } from "vue";
import axios from "axios";
import { useRoute, useRouter } from "vue-router";

const InfoImg = ref('');
const InfoId = ref('');
const Password = ref('');
const InfoName = ref('');
const InfoNickname = ref('');
const InfoEmail = ref('');
const InputImgPath = '';
const router = useRouter();


  onMounted( () => {
    const headerId = sessionStorage.getItem('user');
    axios.get(`http://localhost:1023/mypage/${headerId}`)
          .then(response => {
              console.log(response);
              return response.data;
          })
          .then(user => {
              console.log(user);
              InfoId.value = `${user.userId}`
              InfoImg.value = `${user.userImagePath ? user.userImagePath : 
                "src/assets/mypage/img/male_user.png"}`;
              InfoId.value = `${user.id}`;
              InfoName.value = `${user.userName}`;
              InfoNickname.value = `${user.nickname}`;
              InfoEmail.value = `${user.userEmail}`;
            })
            .catch(err => console.error(err));
  })

  function changeProfile(e){ 	
    console.log(e.target.files[0]);
    document.querySelector('#userImage').src=URL.createObjectURL(e.target.files[0]);
    document.querySelector('#userImage').addEventListener("load", function(){
        URL.revokeObjectURL(this.src);
    })} 

  function userInfoEdit(){
    console.log("put request");
    console.log("Password.value : " + Password.value);
    if(Password.value === ''){
      console.log("password null");
      window.alert("패스워드를 입력하세요");
    } else{
    axios.put(`http://localhost:1023/mypage/${InfoId.value}`, {
        id: InfoId.value,
        userPassword: Password.value,
        userName: InfoName.value,
        nickname: InfoNickname.value,
        userEmail: InfoEmail.value,
        userImagePath: InfoImg.value
    },{
        headers: {
          "Content-Type": "application/json",
        },
      })
    .then((response) => {
      console.log("put response", response.data);
      window.alert(response.data);
      router.push("/mypage");
    })
    .catch((response) => {
      console.log("put error", response.response.data);
      window.alert(response.response.data);
    });
  }
}

function userDelete() {
  console.log("delete request", InfoId.value);
  axios
    .delete(`http://localhost:1023/mypage/${InfoId.value}`)
    .then((response) => {
      console.log("delete response", response);
      sessionStorage.removeItem("token");
      sessionStorage.removeItem("user");
      window.alert(response.data);
      router.push("/");
    });
}
</script>

<style scoped>
@import "@/assets/mypage/edit.css";
.not-overflow-y {
  height: 100%;
  overflow-y: hidden;
}
</style>
