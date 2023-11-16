<template>
  <div>가게 등록 페이지</div>
  가게명
  <input
    type="text"
    v-model="storeCreateRequestDTO.storeRequestDTO.storeName"
  />
  <br />
  가게번호
  <input
    type="text"
    v-model="storeCreateRequestDTO.storeRequestDTO.phoneNumber"
  />
  <br />
  가게주소
  <input type="text" v-model="storeCreateRequestDTO.storeRequestDTO.address" />
  <br />
  대표 이미지 <input type="file" /> <br />
  설명
  <input
    type="text"
    v-model="storeCreateRequestDTO.storeRequestDTO.storeDesc"
  />
  <h3>메뉴</h3>
  메뉴이름
  <input
    type="text"
    v-model="storeCreateRequestDTO.menuRequestListDTO.menuName"
  />
  <br />
  메뉴이미지<input type="file" name="menuImages" />
  <button @click="createStore">저장</button>
  <input type="reset" value="취소" />
</template>
<script setup>
import axios from "axios";
import { ref } from "vue";
let storeCreateRequestDTO = ref({
  storeRequestDTO: {
    storeName: "",
    phoneNumber: "",
    address: "",
    storeDesc: "",
  },
  menuRequestListDTO: [
    {
      menuName: "",
    },
  ],
});
function createStore() {
  // console.log(storeCreateRequestDTO.value);
  axios
    .post("http://localhost:1023/store", storeCreateRequestDTO.value)
    .then((response) => console.log(response))
    .catch((err) => console.log(err));
}
</script>
<style scoped></style>


<!-- <template>
  <div>가게 등록 페이지</div>
  가게명
  <input type="text" v-model="storeName" />
  <br />
  가게번호
  <input type="text" v-model="phoneNumber" />
  <br />
  가게주소
  <input type="text" v-model="address" />
  <br />
  대표 이미지 <input type="file" /> <br />
  설명
  <input type="text" v-model="storeDesc" />
  <h3>메뉴</h3>
  메뉴이름
  <input type="text" v-model="menuName" />
  <br />
  메뉴이미지<input type="file" name="menuImages" />
  <button @click="createStore">저장</button>
  <input type="reset" value="취소" />
</template>
<script setup>
import axios from "axios";
import { ref } from "vue";

// 초기값에 빈 객체와 빈 배열을 가진 객체를 사용
let { storeRequestDTO, menuRequestListDTO } = ref({
  storeRequestDTO: {
    storeName: "",
    phoneNumber: "",
    address: "",
    storeDesc: "",
    storeImagePath: "",
  },
  menuRequestListDTO: [
    {
      menuName: "",
    },
  ],
});

function createStore() {
  let storeCreateRequestDTO = { storeRequestDTO, menuRequestListDTO };
  axios
    .post("http://localhost:1023/store", storeCreateRequestDTO)
    .then((response) => console.log(response))
    .catch((err) => console.log(err));
}
</script>
<style scoped></style> -->