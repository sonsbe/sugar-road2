<template>
  <div class="container">
    <div class="store-like-back">
      <a href="/store">◀</a>
      <Suspense>
        <Recommendation v-if="data!=undefined" :data = "data"></Recommendation>
      </Suspense>
    </div>
    <h3>
      <div>{{ storeInfo.storeName }}</div>
    </h3>
    <div class="edit-remove-btnBox" v-if="storeInfo.userId === userId">
      <button class="buttons" @click="router.push('/store/edit/' + storeId)">
        수정
      </button>
      <RouterLink to="/store">
        <button class="buttons" @click="deleteStore">삭제</button>
      </RouterLink>
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
  <!-- <div><b>Store Location</b></div> -->
  <KakaoMap
    :address="storeInfo.address"
    v-if="storeInfo.address != undefined"
  ></KakaoMap>
  <div class = "right">
    <RouterLink :to="'/review/write/store/' + storeId">
      <button class="buttons bold" style="width:30%; padding : 2% 5% 2% 5%; margin : 4% 0 4% 0; background-color: var(--pink2); border-radius: 10px; color : black">리뷰 작성</button>
    </RouterLink>
  </div>
  <Cards
    :reviewPageURL="'http://localhost:1023/review/of/store/' + storeId"
  ></Cards>
</template>

<script setup>
import axios from "axios";
import { useRoute, useRouter } from "vue-router";
import { onMounted, reactive, ref } from "vue";
import StoreMenuCard from "@/components/store/StoreMEnuCard.vue";
import Cards from "@/components/review/Cards.vue";
import KakaoMap from "@/components/util/KakaoMap.vue";
import Recommendation from '@/components/recommendation/Recommendation.vue';

const currentRoute = useRoute();
const router = useRouter();

let storeId = currentRoute.params.storeId;
let storeInfo = ref({});
let menuList = ref();
let userId = sessionStorage.getItem("user");
const data = {
  recommendation : {
    href: `http://localhost:1023/recommendation/reference-type/S/reference-id/${storeId}`
  },
  createRecommendation : {
    href: `http://localhost:1023/recommendation/reference-type/S/reference-id/${storeId}`
  },
  deleteRecommendation : {
    href: `http://localhost:1023/recommendation/reference-type/S/reference-id/${storeId}`
  }
};

console.log("user", userId);
onMounted(async () => {
  await getStoreInfo();
  console.log("체크", storeInfo.value.userId);
  console.log("storeInfo.userId", storeInfo.userId);
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
  }
}
</script>

<style scoped>
@import "@/assets/storeDetail.css";
</style>
