<template>
  <button class="backBtn" @click="goBack">◀</button>
  <div class="store-write-title" v-if="uri.includes('edit')">가게 수정</div>
  <div class="store-write-title" v-else>가게 등록</div>
  <div class="HeadLine"></div>
  <!-- 테스트 끝나면 입력 필수로 설정 -->
  <div class="inputBox">
    <span>가게명</span>
    <input type="text" v-model="storeRequestDTO.storeName" />
  </div>
  <div class="inputBox">
    <span>가게번호</span>
    <input type="text" v-model="storeRequestDTO.phoneNumber" />
  </div>
  <div class="inputBox">
    <span>가게주소</span>
    <input type="text" v-model="storeRequestDTO.address" />
  </div>
  <div class="inputBox">
    <span>대표이미지</span>
    <input type="file" @change="handleStoreImageChange" />
  </div>
  <div class="inputBox">
    <span>설명</span> <input type="text" v-model="storeRequestDTO.storeDesc" />
  </div>
  <h4>메뉴</h4>
  <div class="menuBox">
    <div class="menu" v-for="(menu, index) in menuRequestListDTO" :key="index">
      <div class="inputBox">
        <span>메뉴이름</span>
        <input type="text" v-model="menu.menuName" />
      </div>
      <div class="inputBox">
        <span> 메뉴이미지</span>
        <input type="file" @change="handleMenuImageChange($event, index)" />
      </div>
    </div>
  </div>
  <br />
  <div class="store-menu-btnBox">
    <button type="button" @click="addMenu">메뉴➕</button>
    <button type="button" @click="deleteMenu">메뉴➖</button>
  </div>
  <br />
  <div class="store-insert-btnBox">
    <button >취소</button>
    <button @click="createStore">저장</button>
  </div>
</template>

<script setup>
import axios from "axios";
import { onMounted, ref } from "vue";
import { useRouter, useRoute } from "vue-router";

const router = useRouter();
const currentRoute = useRoute();
let uri = window.location.pathname;
const store = ref({});
const storeId = currentRoute.params.storeId;

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
  const file = e.target.files[0];

  // 메뉴이미지 변경
  if (file) {
    // 해당 인덱스의 요소가 미리 정의되어 있지 않으면 초기화
    if (!menuRequestListDTO[index]) {
      menuRequestListDTO[index] = {
        menuName: "",
        menuImagePath: null,
      };
    }
    menuRequestListDTO[index].menuImagePath = file;
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
  console.log("testpath", menuRequestListDTO.value[0].menuImagePath);
  console.log("testname", menuRequestListDTO.value[0].menuName);
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
  if (uri.includes("edit")) {
    axios
      .put("http://localhost:1023/store/" + storeId, formData, {
        headers: {
          "Content-Type": "multipart/form-data",
        },
      })
      .then((response) => {
        console.log(response);
        router.push("/store/" + storeId);
      })
      .catch((err) => console.log(err));
  } else {
    axios
      .post("http://localhost:1023/store", formData, {
        headers: {
          "Content-Type": "multipart/form-data",
        },
      })
      .then((response) => console.log(response))
      .catch((err) => console.log(err));
  }
}

onMounted(() => {
  if (uri.includes("edit")) {
    axios
      .get("http://localhost:1023/store/" + storeId)
      .then((response) => {
        console.log(response);
        // store.value = response;
        storeRequestDTO.value.storeName = response.data.storeName;
        storeRequestDTO.value.phoneNumber = response.data.phoneNumber;
        storeRequestDTO.value.address = response.data.address;
        storeRequestDTO.value.storeDesc = response.data.storeDesc;
        storeRequestDTO.value.storeImagePath = response.data.storeImagePath;
        menuRequestListDTO.value[0].menuName =
          response.data.menuDTOList[0].menuName;
      })
      .catch((err) => console.log(err));
  }
});

function goBack() {
  router.go(-1);
}
</script>

<style scoped>
@import "../../../src/assets/store.css";
</style>
