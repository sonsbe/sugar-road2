<template>
  <div class="app-body">
    <div class="content">
      <div class="container">
        <div class="store-like-back">
          <button id="backBtn" @click="goBack">◀</button>
          <span>💖</span>
        </div>
        <h3>
          <div>{{ storeInfo.storeName }}</div>
        </h3>
        <div class="edit-remove-btnBox">
          <button
            class="buttons"
            @click="router.push('/store/edit/' + storeId)"
          >
            수정
          </button>
          <button class="buttons" @click="deleteStore">삭제</button>
          <br />
        </div>
        <img
          v-bind:src="`http://localhost:1023${storeInfo.storeImagePath}`"
          class="store-image"
          alt="가게 대표 이미지"
        />
      </div>
      <div class="address-phone-box">
        <div class="addressBox">
          <!-- <i class="fa-solid fa-map-location-dot" style="color: #999999"></i
          >&nbsp -->
          &nbsp🏠&nbsp&nbsp&nbsp
          <div>{{ storeInfo.address }}</div>
        </div>
        <div class="phoneBox">
          &nbsp📞&nbsp &nbsp
          <div>{{ storeInfo.phoneNumber }}</div>
        </div>
        <div class="descBox">
          &nbsp📃&nbsp&nbsp
          <div>{{ storeInfo.storeDesc }}</div>
        </div>
      </div>
      <!--메뉴 이름/이미지 없을 경우에 뜨지 않도록-->
      <h4 class="menuTitle">Menu</h4>
      <hr />
      <div class="menu-container">
        <StoreMenuCard
          class="menuItem"
          v-for="menu in menuList"
          :key="menu.menuId"
          :menuInfo="{
            storeId: menu.storeId,
            menuName: menu.menuName,
            menuId: menu.menuId,
            menuImagePath: menu.menuImagePath,
            menuDesc: menu.menuDesc,
          }"
        ></StoreMenuCard>
      </div>
      <br />
      <div><b>Store Location</b></div>
      <div id="map" style="width: 100%; height: 200px">지도위치</div>
    </div>
  </div>
</template>
<script setup>
import axios from "axios";
import { useRoute, useRouter } from "vue-router";
import { onMounted, reactive, ref } from "vue";
import StoreMenuCard from "../../components/store/StoreMEnuCard.vue";
const currentRoute = useRoute();
const router = useRouter();
let storeId = currentRoute.params.storeId;
let storeInfo = ref({});
let menuList = ref();
onMounted(async () => {
  await getStoreInfo();
  console.log(storeInfo);
  menuList.value = storeInfo.value.menuDTOList;
  console.log(menuList);
});
async function getStoreInfo() {
  try {
    const response = await axios.get(`http://localhost:1023/store/${storeId}`);
    storeInfo.value = response.data;
    console.log(storeInfo.value);
  } catch (error) {}
}

function deleteStore() {
  console.log(storeId);
  let result = confirm("정말 삭제하시겠습니까?");
  if (result) {
    axios
      .delete(`http://localhost:1023/store/${storeId}`)
      .then((response) => {
        console.log(response);
      })
      .catch((err) => console.log(err));
    router.push({
      path: "/store",
    });
  }
}
function goBack() {
  router.go(-1);
}

</script>

<style scoped>
@import "../../../src/assets/storeDetail.css";
</style>
