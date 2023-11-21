let backendHost;

const hostname = window && window.location && window.location.hostname;

if (hostname === 'localhost'){
    backendHost = "";
}
// 로컬 테스트랑 배포 환경 상관없이 서비스 쓸 수 있도록
// 호스트 URL을 자동 바꾸는 코드

export const API_BASE_URL = `${backendHost}`;