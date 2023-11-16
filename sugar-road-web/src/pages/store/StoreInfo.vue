<template>
  <div class="app-body">
    <div class="content">
      <div class="container">
        스토어 상세 페이지 //
        <div class="store-like-back">
          <button @click="goBack">◀</button>
          <span>좋아요 수:</span>
        </div>
        <h3>
          <div>가게명: {{ storeInfo.storeName }}</div>
        </h3>
        <div class="edit-remove-btnBox">
          <a th:href="#">수정</a>
          <a th:href="#">삭제</a><br />
        </div>
        <img class="store-image" alt="가게 대표 이미지" />
      </div>
      <div class="address-phone-box">
        <div class="addressBox">
          <i class="fa-solid fa-map-location-dot" style="color: #999999"></i
          >&nbsp
          <div>🏠 Store Address: {{ storeInfo.address }}</div>
        </div>
        <div class="phoneBox">
          <i class="fa-solid fa-phone" style="color: #878787"></i> &nbsp
          <div>📞Store Number: {{ storeInfo.phoneNumber }}</div>
        </div>
        <div class="descBox">
          <i class="fa-solid fa-store" style="color: #878787"></i>&nbsp
          <div>📃Stroe Description: {{ storeInfo.storeDesc }}</div>
        </div>
      </div>
      <!--메뉴 이름/이미지 없을 경우에 뜨지 않도록-->
      <h4>Menu</h4>
      <hr />
      <div class="menu-container">
        <StoreMenuCard v-for="menu in menuList"
        :key="menu.menuId"
        :menuInfo="{
          storeId:menu.storeId,
          menuName:menu.menuName,
          menuId: menu.menuId,
          menuImagePath: menu.menuImagePath,
          menuDesc: menu.menuDesc
        }"></StoreMenuCard>
        <!-- <div class="menu">
          <div th:each="mList : ${mlist}" class="swiper-slide">
            <img alt="메뉴 이미지" />
            <div th:text="${mList.menuName}">메뉴 이름</div>
          </div>
        </div> -->
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
  console.log(storeInfo.value.menuDTOList);
  menuList.value = storeInfo.value.menuDTOList;
  console.log(menuList);
});
async function getStoreInfo() {
  try {
    const response = await axios.get(`http://localhost:1023/store/${storeId}`);
    storeInfo.value = response.data;
    console.log(storeInfo.value);
  } catch (error) {
    console.log(error);
  }
}
function goBack() {
  router.go(-1);
}
</script>

<style scoped>
.store-detail-name {
  margin-bottom: 4vh;
}
.store-like-back {
  margin: 1vh 2vh 5vh 0;
  display: flex;
  justify-content: space-between;
}
.store-like-back > a {
  text-decoration: none;
  color: gray;
}
.edit-remove-btnBox {
  text-align: right;
  margin: 5vh 0 1vh 0;
}
.edit-remove-btnBox > a {
  margin-right: 2vh;
  text-decoration: none;
  color: #999999;
  font-size: 13px;
}
.store-image {
  width: 100%;
  height: 25vh;
  object-fit: cover;
  border: 1px solid black;
}
.address-phone-box {
  padding: 4vh 0 3vh 2vh;
  font-size: 15px;
}
.addressBox,
.phoneBox,
.descBox {
  display: flex;
}
.addressBox,
.phoneBox,
.descBox {
  margin-bottom: 1.5vh;
}
.addressBox > div {
  font-size: 14px;
}
.addressBox > i,
.phoneBox > i,
.descBox > i {
  margin-right: 10px;
}
</style>
