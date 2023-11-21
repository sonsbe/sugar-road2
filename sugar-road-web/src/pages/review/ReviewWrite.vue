<template>
    <Write :data="data"></Write>
</template>
<script setup>
    import Write from '@/components/review/Write.vue';
    import {ref} from 'vue';
    import { useRoute } from "vue-router";
    const currentRoute = useRoute();
    const storeId = currentRoute.params.storeId;
    const data = ref({
        storeId : storeId,
        content : "",
        reviewImagePath : ""
    });
    console.log(data);
</script>