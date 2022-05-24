# 토탈쇼핑

## 토탈쇼핑 개요

네이버 쇼핑 api를 이용한 애플리케이션으로 찾고싶은 상품의 최저가를 빠르게 검색해주며, SQLite를 구현하여 로그인 없이도 관심 있는 제품을 추가, 읽기, 삭제가 가능하도록 구현하였습니다.

## 개발일지

### 2022.05.09
아이디어 구상 및 '토탈쇼핑' 어플 개발 시작

### 2022.05.10
네이버 쇼핑 검색 api에서 받아온 Json 자료들을  Retrofit 라이브러리를 이용하여 호출  
호출한 값들을 리사이클러뷰를 활용하여 리스트로 값을 배열해줌  
setOnQueryTextListener를 이용해 텍스트를 입력하고 검색 버튼을 누르면 그에 따른 결과가 리사이클러뷰 리스트로 출력  

### 2022.05.11
검색 결과에 따른 세가지의 필터 기능 을 추가로 제공할수 있도록 함  
1. (20개,50개,100개) 각각에 따른 검색 결과에 개수를 조절  
2. (인기상품순, 신상품순, 최저가순, 최고가순) 네이버 검색 API의 있는 기능으로 검색 결과를 각 필터에 따라 문자 그대로 정렬  
3. (세로정렬, 액자정렬) 기존 '세로정렬'에서 layoutmanager 내의 GridLayoutManager 배열을 활용한 '액자정렬' 기능을 추가하여 자기가 보기 편한 방식으로 정렬 가능  

### 2022.05.12
필터를 고를떄마다 그에 맞는 알림이 뜨도록 Snackbar 기능을 사용  
setOnRefreshListener 을 이용해 상하 스와이프를 화면 새로고침을 하도록 구현  
전반적인 디자인 수정

### 2022.05.13
SQLite 데이터베이스를 이용하여 '관심 항목' 기능 추가  
setOnLongClickListener와 AlertDialog를 활용하여 검색 결과로 나온 리사이클러뷰의 아이템을 길게 누르면 확인 메시지와 함께 데이터가 데이터베이스에 저장  
onBackPressed를 이용하여 뒤로 버튼을 누른뒤 3초이내 한번 더 누르면 앱이 종료되도록 구현  

### 2022.05.14
안드로이드 툴바 구현
ActionBarDrawerToggle 로 네비게이션 드로어 구현, 목록으로 '관심 항목', '나가기', '개발자 정보' 추가  
SQLite로 저장되있는 데이터들을 리사이클러뷰를 이용해 '관심 항목' 에서 리스트로 보여줌  
이 리스트들의 아이템을 마찬가지로 setOnLongClickListener와 AlertDialog를 활용하여 아이템을 길게 누르면 확인 메시지와 함께 기존의 데이터가 삭제되도록 구현  
'전체 항목 삭제' 버튼을 누르면 리사이클러뷰 리스트와 SQLite 데이터들이 전부 삭제  

### 2022.05.16
isNetworkAvailable를 이용해 네트워크에 연결되어 있지 않으면 알림과 함께 앱이 종료  
검색 버튼을 누르면 키보드가 숨겨지도록 변경

### 2022.05.17
스플래시 화면 구현, 앱 아이콘 추가  

### 2022.05.19
구글 플레이 콘솔 앱 업로드(검토 중)

## 2022.05.24
구글 플레이 업로드 성공
네비게이션 드로어 바 내의 '사용 설명서' 추가
'액자 정렬' 기능 사용 시 리사이클러뷰 아이템 간격이 일정하지 않은 버그 수정
