<template>
  <h3>Store page</h3>
  <!-- <router-link :to="'/store/write'">
    <button @onClick="createStore">가게 등록</button>
  </router-link> -->
  <StoreCard
    class="container"
    v-for="store in storeList.value"
    :key="store.storeId"
    :storeData="{
      storeName: store.storeName,
      address: store.address,
      phoneNumber: store.phoneNumber,
      storeDesc: store.storeDesc,
      storeImagePath: store.storeImagePath,
      storeId: store.storeId,
      viewsCount:store.viewsCount
    }"
  >
  </StoreCard>
  <router-link :to="'/store/write'">
    <div class="store-insertBtn bold h5" @onClick="createStore">가게 등록</div>
  </router-link>
</template>
<script setup>
import axios from "axios";
import StoreCard from "../../components/store/StoreCard.vue";
import { onMounted, reactive } from "vue";
let storeList = reactive([]);
onMounted(async () => {
  await getStoreList();
   console.log(storeList.value);
});
async function getStoreList() {
  try {
    const response = await axios.get("http://localhost:1023/store");
    storeList.value = response.data;
    console.log(storeList.value);
  } catch (err) {
    console.log(err);
  }
}
</script>
<style scoped>
.store-insertBtn {
  text-decoration: none;
  padding: 10px;
  width: 100%;
  display: inline-block;
  position: sticky;
  background-color: #decfed;
  text-align: center;
  border-radius: 5px;
  bottom: 3%;
  z-index: 99999;
  /*   font-size: 20px;*/
  box-shadow: rgba(0, 0, 0, 0.15) 1.95px 1.95px 2.6px;
  border: 1px solid gray;
}
.store-insertBtn:hover {
  background-color: #cab9dc;
}
@import "../../../src/assets/store.css";
</style>
