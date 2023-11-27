<template>
    <div id="map"></div>
</template>

<script setup>
    import {defineProps, ref, onMounted} from 'vue';
    const props = defineProps({
        address:String
    });
    
    function loadScript(){
        const script = document.createElement("script");
        script.setAttribute("autoload", false);
        script.setAttribute("type", 'text/javascript');
        script.src="http://dapi.kakao.com/v2/maps/sdk.js?appkey=e9f0b153df219037e9402a0154f2ca62&libraries=services";
        script.onload = () => window.kakao.maps.load(loadMap());
        document.head.appendChild(script);
    }
    function loadMap(){
        console.log("loadMap 호출");
        var container = document.getElementById('map');
        var options = {
            center: new window.kakao.maps.LatLng(33.450701, 126.570667),
            level: 3
        };
        var icon = new window.kakao.maps.MarkerImage(
            '../../css/marker/Marker.png',
            new window.kakao.maps.Size(53, 60)
        );
        var map = new window.kakao.maps.Map(container, options);
        var zoomControl = new window.kakao.maps.ZoomControl();
        map.addControl(zoomControl, window.kakao.maps.ControlPosition.RIGHT);
        var geocoder = new window.kakao.maps.services.Geocoder();
        geocoder.addressSearch(
            props.address, 
            function(result, status){
                if(status === window.kakao.maps.services.Status.OK){
                    var coords = new window.kakao.maps.LatLng(result[0].y, result[0].x);
                    var marker = new window.kakao.maps.Marker({
                        map: map,
                        position: coords,
                        image: icon
                    });
                    map.setCenter(coords);
            }
        });
    }
    onMounted(
        () => {
            if (window.kakao && window.kakao.maps && window.kakao.maps.LatLng) {
                loadMap();
            }  
            else {
                loadScript();
            }
        } 
    );
</script>