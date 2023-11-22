<template>
  <!-- <div class="form-signin w-100 m-auto">
    <h1 class="h3 mb-3 fw-normal">JWT 로그인 테스트</h1>
    <div class="form-floating">
      <input type="text" class="form-control" id="floatingInput" placeholder="사용자 ID"
            @keyup.enter="submit()" v-model="state.form.id">
      <label for="floatingInput">계정(id)</label>
    </div>
    <div class="form-floating">
      <input type="password" class="form-control" id="floatingPassword" placeholder="Password" 
      @keyup.enter="submit()" v-model="state.form.password">
      <label for="floatingPassword">암호</label>
    </div>    
    <button class="w-100 btn btn-lg btn-primary" @click="login()">로그인</button>
    <hr>
    <button class="w-100 btn btn-lg btn-danger" @click="check()">로그인 상태 채크</button>
  </div> -->

  <div class="app-body">
    <div class = "content">
            <img id="loginTitle" src="src/assets/users/img/Sugar-Road Logo.png">
        <div id="loginBox">
            <form name="loginForm">
                <h4 id="please">Please Login</h4>
                <input id="loginText1" type="text" name="userId" @keyup.enter="submit()" v-model="state.form.id" placeholder="Id" autocomplete='off' required><br>
                <br>
                <input id="loginText2" type="password" name="userPassword" @keyup.enter="submit()" v-model="state.form.password" placeholder="Password" required><br>
                <br>
                <!-- <input type="submit" id="signIn" value="Login"> -->
             </form>
             <button id="signIn" @click="login">Login</button>
                <br>
                <button id="signUp" onclick="location.href = '/signup'">Go to Sign Up</button>
        </div>
    </div>
  </div>
</template>

<script>
import {reactive} from "vue";
import axios from "axios";
import { useRoute, useRouter } from "vue-router";

axios.defaults.withCredentials = true;

export default {
  setup() {
    const router = useRouter();
    const state = reactive({
      form: {
        id: "",
        password: ""
      }
    })

    const login = async () => {      
      const response = await axios.post("http://localhost:1023/login", state.form, 
        {headers : {
          "Content-Type": "application/json",
          "X-Requested-With": "XMLHttpRequest"
          // "withCredentials" : true
          //토큰 값 가져와야하면
          //sessionStorage.getItem('token')
          //Authorization : 'token'
        }
        }).then((response)=>{
          console.log("헤더확인");
          console.log(response.headers.user);
          console.log(response.headers.authorization);
          sessionStorage.setItem('token',response.headers.authorization);
          sessionStorage.setItem('user', response.headers.user)
          
          window.alert(`반갑습니다, ${response.headers.user} 님!`);
          router.push('/')
          //토큰 값 가져와야하면
          //sessionStorage.getItem('token')
        });
    }

    const logout = () => {  
          sessionStorage.removeItem("token");
          sessionStorage.removeItem("user");
          window.alert("로그아웃 수행");
    }

    const check = () => {      
      axios.get("http://localhost:1023/check",{
            headers: {
              'Authorization': sessionStorage.getItem("token")
            }
          }).then((res) => {       
        window.alert(res.data);
      });
    }

    return {state, login, logout, check}
  }
}
</script>

<style scoped>
@import "src/assets/users/login.css";
</style>