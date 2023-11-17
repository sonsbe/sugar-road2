<template>
  <div>가게 등록 페이지</div>
  <button @click="goBack">◀</button>
  <!-- 테스트 끝나면 입력 필수로 설정 -->
  <br />
  가게명
  <input type="text" v-model="storeRequestDTO.storeName" />
  <br />
  가게번호
  <input type="text" v-model="storeRequestDTO.phoneNumber" />
  <br />
  가게주소
  <input type="text" v-model="storeRequestDTO.address" />
  <br />
  대표이미지
  <input
    type="file"
    id="storeImage"
    class="storeImage"
    @change="handleStoreImageChange"
  /><br />
  설명
  <input type="text" v-model="storeRequestDTO.storeDesc" />
  <h3>메뉴</h3>
  <div class="menuBox">
    <div class="menu" v-for="(menu, index) in menuRequestListDTO" :key="index">
      메뉴이름
      <input type="text" v-model="menu.menuName" />
      <br />
      메뉴이미지
      <input type="file" @change="handleMenuImageChange($event, index)" />
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
import { useRouter } from "vue-router";

const router = useRouter();

let storeRequestDTO = ref({
  storeName: "",
  phoneNumber: "",
  address: "",
  storeDesc: "",
  storeImagePath: null,
});

let menuRequestListDTO = ref([
  {
    menuName: "",
    menuImagePath: null,
  },
]);

function handleStoreImageChange(e) {
  // 대표이미지 변경
  storeRequestDTO.value.storeImagePath = e.target.files[0];
  console.log(storeRequestDTO.value.storeImagePath);
}

function handleMenuImageChange(e, index) {
  console.log(e);
  console.log(e.target.files);
  const file = e.target.files[0];

  console.log(file);

  // 메뉴이미지 변경
  if (file) {
    // 해당 인덱스의 요소가 미리 정의되어 있지 않으면 초기화
    // if (!menuRequestListDTO[index]) {
    //   menuRequestListDTO[index] = {
    //     menuName: "",
    //     menuImagePath: null,
    //   };
    // }
    menuRequestListDTO.value[index].menuImagePath = file;
    console.log(menuRequestListDTO.value[index].menuImagePath);
  }
}

function createStore() {
  console.log(menuRequestListDTO.value);
  console.log(menuRequestListDTO[0]);
  const formData = new FormData();

  formData.append("storeName", storeRequestDTO.value.storeName);
  formData.append("phoneNumber", storeRequestDTO.value.phoneNumber);
  formData.append("address", storeRequestDTO.value.address);
  formData.append("storeDesc", storeRequestDTO.value.storeDesc);
  formData.append("storeImagePath", storeRequestDTO.value.storeImagePath);
  console.log("test", menuRequestListDTO.value[0].menuImagePath);

  let menuDatas = [
    {
      menuName: menuRequestListDTO.value[0].menuName,
      menuImagePath: menuRequestListDTO.value[0].menuImagePath,
    },
  ];
  const json = JSON.stringify(menuDatas);
  const blob = new Blob([json], { type: "application/json" });
  formData.append("menuRequestListDTO", blob);

  console.log(formData);
  // menuRequestListDTO.value.forEach((menu, index) => {
  //   formData.append(`menuRequestListDTO[${index}].menuName`, menu.menuName);
  //   formData.append(
  //     `menuRequestListDTO[${index}].menuImage`,
  //     menu.menuImagePath
  //   );
  // });

  axios
    .post("http://localhost:1023/store", formData, {
      headers: {
        "Content-Type": "multipart/form-data",
      },
    })
    .then((response) => console.log(response))
    .catch((err) => console.log(err));
}
</script>

<style scoped></style>
