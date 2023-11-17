<template>
  <div>가게 등록 페이지</div>
  <button @click="goBack">◀</button>
  <!-- 테스트 끝나면 입력 필수로 설정 -->
  <br />
  가게명
  <input type="text" v-model="storeRequestDTO.storeName" required />
  <br />
  가게번호
  <input type="text" v-model="storeRequestDTO.phoneNumber" />
  <br />
  가게주소
  <input type="text" v-model="storeRequestDTO.address" />
  <br />
  대표이미지
  <div>
    <input type="file" id="storeImage" @change="fileHandler($event)" hidden />
    <label for="storeImage">
      <img class="storeImage" src="@/assets/store/imgup.png" />
    </label>
  </div>
  설명
  <input type="text" v-model="storeRequestDTO.storeDesc" />
  <h3>메뉴</h3>
  <div class="menuBox">
    <div class="menu" v-for="(menu, index) in menuRequestListDTO" :key="index">
      메뉴이름
      <input type="text" v-model="menu.menuName" />
      <br />
      메뉴이미지
      <input type="file" name="menuImages" />
    </div>
  </div>
  <br />
  <div class="store-menu-btnBox">
    <button type="button" @click="addMenu">메뉴➕</button>
    <button type="button" @click="deleteMenu">메뉴➖</button>
  </div>
  <br />
  <button @click="createStore">저장</button>
  <input type="reset" value="취소" />
</template>

<script setup>
import axios from "axios";
import { ref } from "vue";
import { useRoute, useRouter } from "vue-router";
const router = useRouter();
let formData = new FormData();
let storeRequestDTO = ref({
  storeName: "",
  phoneNumber: "",
  address: "",
  storeDesc: "",
  storeImagePath: {},
});
let menuRequestListDTO = ref([
  {
    menuName: "",
  },
]);
// let storeCreateRequestDTO = ref({ storeRequestDTO, menuRequestListDTO });

function fileHandler(e) {
  console.log(e.target.files[0]);
  storeRequestDTO.storeImagePath = e.target.files[0];
  console.log(storeRequestDTO.storeImagePath);
}
formData.append("storeRequestDTO.storeName", storeRequestDTO.storeName);
formData.append("storeRequestDTO.phoneNumber", storeRequestDTO.phoneNumber);
formData.append("storeRequestDTO.address", storeRequestDTO.address);
formData.append("storeRequestDTO.storeDesc", storeRequestDTO.storeDesc);
formData.append("menuRequestListDTO.menuName", menuRequestListDTO.menuName);

function createStore() {
  // console.log(storeCreateRequestDTO.value);
  axios
    .post("http://localhost:1023/store", formData, {
      headers: {
        "Content-Type": "multipart/form-data",
      },
    })
    .then((response) => console.log(response))
    .catch((err) => console.log(err));
}

function addMenu() {
  storeCreateRequestDTO.value.menuRequestListDTO.push({ menuName: "" });
}

function deleteMenu() {
  let menuCount = storeCreateRequestDTO.value.menuRequestListDTO.length;
  if (menuCount > 1) {
    storeCreateRequestDTO.value.menuRequestListDTO.pop();
  }
}
function goBack() {
  router.go(-1);
}
</script>

<style scoped>
.storeImage {
  width: 50px;
}
</style>
