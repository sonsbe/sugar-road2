<template>
  <div class="form-signin w-100 m-auto">
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
    <button class="w-100 btn btn-lg btn-warning" @click="logout()">로그아웃</button>
    <hr>
    <button class="w-100 btn btn-lg btn-danger" @click="check()">로그인 상태 채크</button>
  </div>
</template>

<script>
import {reactive} from "vue";
import axios from "axios";

axios.defaults.withCredentials = true;

export default {
  setup() {
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
          console.log(response.headers.authorization);
          sessionStorage.setItem('token',response.headers.authorization);
          //토큰 값 가져와야하면
          //sessionStorage.getItem('token')
        });

      // fetch("http://localhost:1023/login", {
      //   method : "POST",
      //   body : JSON.stringify(state.form),
      //   headers: {
      //     "Content-Type": "application/json",
      //     // 'Content-Type': 'application/x-www-form-urlencoded',
      //   }
      // }).then((response) => {
      //   console.log(response);
      //   console.log(response.headers);
      //   console.log(response.headers.Authorization);
      //   console.log(response.headers.authorization);
      //   return response.json()})
      // .then((json) => {
      //   console.log(json);
      // })
      // console.log(response)
      // console.log(response.headers.getAuthorization())
      // console.log(response.headers.get('authorization'))

      // .then((res) => {  
      //     console.log(res.headers);
      //     console.log(res.headers.Authorization); // 모두 가능
      //     console.log(res.headers.get('authorization'));
      //     console.log(res.headers.Authorization);
      //     console.log(res.headers.getAuthorization());   
      //     console.log(res.headers['authorization']);

      //     sessionStorage.setItem("token", res.headers.get('Authorization'));

      //     window.alert(res.data);
      //   }).catch(() => {
      //     window.alert("로그인을 수행하는 동안 오류가 발생하였습니다..");
      //   });
    }

    const logout = () => {  
          sessionStorage.removeItem("token");
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

<style lang="scss" scoped></style>
<style scoped>
@import "src/assets/users/login.css";
</style>