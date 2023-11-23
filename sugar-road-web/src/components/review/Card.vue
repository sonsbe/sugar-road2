<template>
    <div class = "v-item pink1 left" style="width:100%">
        <router-link :to="'/review/' + data.id">
            <table class = "v-table" style="width:90%">
                <tr>
                    <td><h5>🍰 {{data.nickname}}</h5></td>
                    <td class = "v-table-quater right t6 bold">
                        {{ data.postedDate.substring(0,10) }}
                    </td>
                </tr>
                <tr>
                    <td colspan="2" class = "t5">
                        <h6>{{"⭐".repeat(data.star)}}</h6>
                    </td>
                </tr>
                <tr>
                    <td colspan="2" class = "t5">
                        {{ data.content }}
                    </td>
                </tr>
                <tr>
                    <td colspan="2" class = "review-image-box">
                        <img :src = "data.reviewImagePath" class = "review-image" v-if = "data.reviewImagePath!=indefined">
                    </td>
                </tr>
                <tr>
                    <td>
                        <Suspense>
                            <Recommendation v-if="data._links!=undefined" :data = "data._links"></Recommendation>
                        </Suspense>
                    </td>
                </tr>
            </table>
        </router-link>
    </div>
</template>
<script setup>
    import {defineProps} from 'vue';
    import Recommendation from '@/components/recommendation/Recommendation.vue';
    const props = defineProps( {
        data : Object
    });
</script>