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
  <!-- <div class="menuBox">
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
  </div>-->
  <div class="menuBox">
    <!-- v-for 디렉티브가 렌더링할 대상이 없을 때 초기 메뉴를 추가합니다. -->
    <div v-if="menuNameList.length === 0" class="menu" :key="0">
      <div class="inputBox">
        <span>메뉴이름</span>
        <input type="text" v-model="menuNameList[0]" />
      </div>
      <div class="inputBox">
        <span> 메뉴이미지</span>
        <input type="file" @change="handleMenuImageChange($event, 0)" />
      </div>
    </div>

    <!-- 기존 메뉴를 나타냅니다. -->
    <div v-for="(menu, index) in menuNameList" :key="index" class="menu">
      <div class="inputBox">
        <span>메뉴이름</span>
        <input type="text" v-model="menuNameList[index]" />
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
    <button>취소</button>
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

// let menuRequestListDTO = ref([
//   {
//     menuName: "",
//     menuImagePath: null,
//   },
// ]);

let menuNameList = ref([]);
let menuImgList = ref([]);

function handleStoreImageChange(e) {
  // 대표이미지 변경
  storeRequestDTO.value.storeImagePath = e.target.files[0];
  console.log(storeRequestDTO.value.storeImagePath);
}

function handleMenuImageChange(e, index) {
  const file = e.target.files[0];

  if (file) {
    menuImgList.value[index] = file;
  }
}

function createStore() {
  const formData = new FormData();

  formData.append("storeName", storeRequestDTO.value.storeName);
  formData.append("phoneNumber", storeRequestDTO.value.phoneNumber);
  formData.append("address", storeRequestDTO.value.address);
  formData.append("storeDesc", storeRequestDTO.value.storeDesc);
  formData.append("storeImagePath", storeRequestDTO.value.storeImagePath);

  // 각 메뉴 정보를 formData에 추가
  menuNameList.value.forEach((menuName, index) => {
    if (menuNameList.value[index]) {
      formData.append(`menuNameList`, menuNameList.value[index]);
    }
  });
  console.log(menuNameList.value[0]);

  menuImgList.value.forEach((menuImg, index) => {
    if (menuImgList.value[index]) {
      formData.append(`menuImgList`, menuImgList.value[index]);
    }
  });

  // ** update **
  if (uri.includes("edit")) {
    axios
      .put("http://localhost:1023/store/" + storeId, formData, {
        headers: {
          "Content-Type": "multipart/form-data",
          accept: "application/json",
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
      .then((response) => {
        router.push("/store");
        console.log(response);
      })
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
        for (let i = 0; i < response.data.menuDTOList.length; i++) {
          menuNameList.value[i] = response.data.menuDTOList[i].menuName;
          menuImgList.value[i] = response.data.menuDTOList[i].menuImagePath;
        }
      })
      .catch((err) => console.log(err));
  }
});

function addMenu() {
  menuNameList.value.push("");
}
function deleteMenu() {
  let menuCount = menuNameList.value.length;
  if (menuCount > 1) {
    menuNameList.value.pop();
  }
}
function goBack() {
  router.go(-1);
}
</script>

<style scoped>
@import "../../../src/assets/store.css";
</style>
