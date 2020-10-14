# RepoList

Github'daki kullanıcıların repolarının listelenebildiği bir uygulamadır.

## Kullanılan Kütüphaneler

Uygulamada kullanılan third party kütüphaneler ve kullanım amaçları aşağıdaki gibidir.

```bash
implementation 'com.facebook.stetho:stetho:1.5.1'
```
Github API'ına atılan istekleri Chrome tarayıcıdaki inspect eklentisi aracılığıyla daha kolay görebilmek için eklenmiştir.

```bash
implementation 'com.github.santalu:emptyview:1.3.6'
```
Listeleme sayfasında servise istek atıldığında ekranda progress göstermek içerik geldiğinde içeriği, içerik olmadığı durumda içerik bulunamadı mesajını, API'da hata olduğunda hata durumlarını göstermek için kullanılmıştır.

```bash
implementation 'com.github.bumptech.glide:glide:4.11.0'
```
Detay sayfasında kullanıcının resmini url'den çekip ekranda göstermek için kullanılmıştır.
