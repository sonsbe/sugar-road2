<template>
  <div v-if="p.kind=='r'" class="thumbnail skyblue">
        <router-link :to="'/review/'+p.review.id" :id="p.review.id">
            <table class="v-table">
                <tr>
                    <td>
                        <p class="t5 bold" v-text="p.review.storeId"></p>
                    </td>
                    <td class="v-table-half right">
                        <p class="t6">댓글 수: 3</p>
                    </td>
                </tr>
                <tr>
                    <td>
                        <p v-text="p.review.content"></p>
                    </td>
                </tr>
                <tr>
                    <td colspan="2" class="right">
                            <span v-text="p.review.star"></span>
                    </td>
                </tr>
            </table>
        </router-link>
    </div>
</template>

<script setup>
import { ref } from 'vue';
const p = defineProps({
    review:{
      type: Object
    }
  })
</script>