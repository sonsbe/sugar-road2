<template>
  <div class="thumbnail lightgray">

    <router-link :to="'/store/'+p.store.id" :id="p.store.id">
            <table class="v-table">
                <tr>
                    <td class="left">
                        <p class="t5 bold" v-text="p.store.storeName"></p>
                    </td>
                </tr>
                <tr>
                    <td>
                        <img :src="`${p.store.storeImagePath}? ${p.store.storeImagePath} : '/images/icons/bread.png'`" alt="">
                    </td>
                </tr>
                <tr>
                    <td class="right">
                        <p v-text="p.store.phoneNumber"></p>
                    </td>
                </tr>
            </table>

    </router-link>
    </div>
</template>

<script setup>
import { ref } from 'vue';
const p = defineProps({
    store:{
      type: Object
    }
  })
  console.log("store", p);
</script>