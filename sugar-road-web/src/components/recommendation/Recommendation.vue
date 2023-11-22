<template>
    <button 
        :class = "[isRecommended?'t-pink3':'t-lightgray', 't4', 'bold']"
        @click="clickHandler(data.createRecommendation.href)"
        style = "border : none; background-color: transparent;"
    >
        ♥ {{count}}
    </button>
</template>
<script setup>
    import {defineProps, ref, onMounted} from 'vue';
    import {apiService} from '@/services/ApiService.js';
    const props = defineProps( {
        data : Object
    });
    const count = ref(null);
    const isRecommended = ref(false);
    let recommendation;
    onMounted(
        async () => {
            console.log(props.data);
            recommendation = await apiService.get(props.data.recommendation.href).catch(error => console.log(error));

            count.value = recommendation.data.count;

            isRecommended.value = recommendation.data.isRecommended;
        }
    );

    const clickHandler = async (url) => {
        if (isRecommended.value) {
            recommendation = await apiService.delete(url).catch(error => console.log(error));
        }
        else {
            recommendation = await apiService.post(url).catch(error => console.log(error));
        }
        count.value = recommendation.data.count;
        isRecommended.value = recommendation.data.isRecommended;
    }

</script>
<style scoped>
    /* .t-pink3 {
        color : #F1ABCC;
    }
    .t-lightgray {
        color : #d3d3d3;
    } */
</style>