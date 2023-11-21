import axios from "axios";
import { reactive, ref } from "vue";
import { defineStore } from "pinia";

export const useStoreDataStore = defineStore("storeData", ()=>{
  let storeData = reactive({})
  function getStoreData(){
    axios.get("http://localhost:1023/store")
    .then((response)=>{
      storeData.value = response.data;
      console.log(datas.value);
      return response.data
    }).catch((err)=>console.log(err))
  }
  return {storeData, getStoreData}
})