<template>
    <button :class = "[isRecommended?t-pink3:t-lightgray, t4, bold]"  @click="clickHandler">
        ♥ {{count}}
    </button>
</template>
<script setup>
    import {defineProps, ref, onMounted} from 'vue';
    import {apiService} from '@/services/ApiService.js';
    const props = defineProps( {
        data : Object
    });
    const count = ref(0);
    const isRecommended = ref(false);
    let recommendation;
    onMounted(
        async () => {
            console.log(props.data);
            recommendation = await apiService.get(props.data.recommendation.href).catch(error => console.log(error)).data;

            count.value = recommendation.count;

            isRecommended.value = recommendation.isRecommended;
        }
    );
    

    const clickHandler = async () => {
        if (isRecommended.value) {
            recommendation = await apiService.delete(props.data.deleteRecommendation.href).catch(error => console.log(error));
        }
        else {
            recommendation = await apiService.post(props.data.createRecommendation.href).catch(error => console.log(error));
        }
        count.value = recommendation.data.count;
        isRecommended.value = recommendation.data.isRecommended;
    }

</script>