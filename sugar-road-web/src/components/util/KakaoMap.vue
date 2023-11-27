<template>
    <div id="map"></div>
</template>

<script>
export default {
  name: "MapKakao",
  props: {
    address: {
      type: String,
      required: true,
    },
  },
  data() {
    return {
      map: null,
      zoomControl: null,
      geocoder: null,
    };
  },
  mounted() {
    if (window.kakao && window.kakao.maps) {
      this.loadMap();
    } else {
      this.loadScript();
    }
  },
  methods: {
    loadScript() {
      const script = document.createElement("script");
      script.src =
        "//dapi.kakao.com/v2/maps/sdk.js?appkey=08c3b314b66d3375fe2951369def0dd3&autoload=false&libraries=services";
      script.onload = () => window.kakao.maps.load(this.loadMap);
      document.head.appendChild(script);
    },
    loadMap() {
      const container = document.getElementById("map");
      const options = {
        center: new window.kakao.maps.LatLng(37.514810601946, 127.10666655857),
        level: 4,
      };

      this.map = new window.kakao.maps.Map(container, options);
      this.zoomControl = new window.kakao.maps.ZoomControl();
      this.map.addControl(
        this.zoomControl,
        window.kakao.maps.ControlPosition.RIGHT
      );
      this.geocoder = new window.kakao.maps.services.Geocoder();

      this.geocoder.addressSearch(this.address, (result, status) => {
        if (status === window.kakao.maps.services.Status.OK) {
          const coords = new window.kakao.maps.LatLng(result[0].y, result[0].x);
          const marker = new window.kakao.maps.Marker({
            map: this.map,
            position: coords,
            // Add your marker settings here
          });
          this.map.setCenter(coords);
        }
      });
    },
  },
};
</script>

<style>
#map {
  width: 100%;
  height: 20vh;
}
</style>