# carControlWithSocket

## Özet Bilgi

##### Gerçekleştirilen projede bilgisayar üzerinde çalışan sunucu ile bağlantısı bulunan bulunan raspberry pi ve android işletim sistemine sahip bilgisayar ile çalışan bir robot yapılmıştır. Projede kullanılmak üzere L298N Motor sürücü, Bilgisayar, DC Motor, ArduinoUNO ve bağlantı malzemeleri temin edilmiştir. Gereken bağlantıların yapılabilmesi için bir takım donanım bilgisinin yanısıra Arduino’nun programlanması için C dilinde bilgi sahibi olmak gerekmektedir. Gerekli olan programlama dili hakkında araştırma yapılmış ve devrelerin çalışabilir hale getirilmesi sağlanmıştır. Kablosuz haberleşme için yapılan işlemler sağlandıktan sonra elektronik cihazların kontrolü gerçekleştirilmiştir. Projenin konusu gereği istenen tüm koşullar sağlanmış ve proje sonlandırılmıştır.

## Giriş
##### Projenin konusu, mobil cihazdan kablosuz olarak Socket ile araç kontrolü yapmaktır. DC motorların hareketi mobil cihaz ile sağlanacaktır. Bu yönlendirme işlemi için ise mobil cihazımızda bir uygulama tasarlanacak ve dizayn edilen uygulamadan gerekli bilgiler alınarak mekanik aksam üzerinde ki değişimler gözlenecektir. Teknolojinin ilerlemesiyle birlikte insanların yaşamlarını kolaylaştıran cihazlara yönelmesi ve bu cihazların tek elden kontrol edilmesi istendiği gözlemlenmektedir. Bu kontrol sırasında gereksiz kablo ve kullanımı zor olan aletlerden kaçınılmaktadır. Bu nedenle günümüzde yavaş yavaş bütün cihazların kablosuz cihazlar üzerinden kontrolüne geçiş yapılmaktadır. Mobil cihazlardan, gereken işlerin karşılanması ve gündelik yaşamı kolaylaştıran yeni buluşların insan yaşamı içerisine girmesi oldukça önemlidir. Tüm bu nedenlerden dolayı projeyi değerlendirdiğimizde aslında projenin ne kadar önemli olduğu ve göz önünde bulunduğu ortadadır. 

## Kullanılan Malzemeler
### Raspberry Pi 3 Model B+
*	1.4 GHz dört çekirdekli ARM Cortex-A53 işlemci (64-bit)
*	Çift bant 802.11ac Kablosuz LAN
*	Bluetooth 4.2 desteği
*	USB 2.0 üzerinden Gigabit Ethernet desteği
*	PoE HAT ile Ethernet üzerinden güç desteği
*	USB Yığın depolama önyüklemesi
# ![alt text](https://www.raspberrypi.org/app/uploads/2018/03/770A5842-462x322.jpg)
##### Raspberry Pi 3 Model B+’da bulunan 5 Ghz’lik WiFi desteğinin, nesnelerin interneti (IoT) için varsayılan hale gelmeye başlayan 2.4 Ghz’dan kurtulmasını sağlaması bekleniyor. Yalnızca bilgisayar olarak kullanılmasının yanında çeşitli nesnelerle entegre bir şekilde çalışmasını da sağlayabileceğiniz Raspberry Pi Model 3 B+, bir önceki modeliyle aynı fiyattan satışa sunuluyor. 35 dolardan satışa çıkacak mini bilgisayar, üzerinde bulunan HDMI, USB gibi çıkışları sayesinde bir monitöre bağlayıp internetteki tüm aktivitelerinizi gerçekleştirmenize olanak tanıyor.
### DC MOTORLAR
##### Doğru akım elektrik enerjisini, mekanik enerjiye çeviren elektrik makinesine DC motor denir. Doğru akım motorlarına DA veya DC motor denilmektedir. Doğru akım zamanla yönü ve şiddeti değişmeyen akıma denir. İngilizce “Direct Current” kelimelerinin kısaltılması “DC” ile gösterilir.
### PWM ile DC Motor Hız Kontrol Yöntemi
##### DC motor hız kontrol yöntemleri ‘nden ilki pwm ile hız kontrol yöntemi’dir.Bu yöntem en yaygın hız kontrol yöntemlerinden birisidir. “Pulse Width Modulation” (Darbe genlik modülasyonu) kelimelerinin kısaltılmış halidir. Bir D.C. motorun hızını kontrol edebilmek için ayarlanabilir bir D.C. gerilime ihtiyaç vardır. Eğer 12 v.bir D.C.  motor alır ve enerji verirsek motor hızlanmaya başlayacaktır. Motorun maksimum hıza ulaşması için belirli bir süre geçmesi gerekecektir. Eğer motor maksimum hıza ulaşmadan motorun enerjisini kesersek motor bu defa yavaşlamaya başlayacaktır. Eğer enerjiyi yeterli çabuklukta sürekli kapatıp açarsak motor sıfır ile maksimum arasında bir yerdeki hız değerinde çalışacaktır. İşte pwm tam olarak bu anlama gelir. PWM yöntemi ile motor belirli aralıklarda, darbe işaretleri gönderilerek enerji verilir ve motor belirli bir hızda çalıştırılır. Bu darbe işaretlerinin genliği ayarlanarak motorun enerjili olma süresi artırılıp azaltılabilir. Bu ise motorun çalışma hızının artırılıp azaltılması anlamında gelir.
### L 298N Motor Sürücü Kartı
##### L298N H Bridge Çift Motor Sürücü Kartı genellikle motorların hız ve yönlerini kontrol etmek amacıyla kullanılan bir çift H köprülü motor sürücü kartıdır. Motor kontrolü dışında ışıklandırma projelerinde LED gruplarının parlaklarının ayarlanması amacıyla da kullanılır.

## YAPILAN ÇALIŞMA
##### Akıllı telefon uygulaması yapılırken donanım elemanlarıyla telefonun haberleşmesi zorunludur. Bunun için kablosuz iletişim çok daha önemlidir. Projede bu iletişimi sağlamak için raspberrinin wi-fi özelliği kullanıldı ve yerel ağ üzerinden raspberry ve telefonun haberleşmesi sağlandı.
##### Telefon uygulamamız soket vasıtasıyla robotu kontrol edecek bilgileri üzerinde raspberry bulunan araç kitine soket üzerinden göndermektedir. Kullanılan araç kiti 4 adet DC motora sahip olup ileri,geri,sağ ve sol yönlerde hareket edebilmektedir. Kumandadan yapılan mekanik kontrol yerine Raspberry tarafından kontrol edilmesi gerekmektedir. Ana kumanda tarafından sağlanan analog sinyallerin bizim tarafımızdan ayarlanması sağlanmıştır.

## Kaynakça
* [tıklayınız](http://www.elektrikrehberiniz.com/elektrik-motorlari/dc-motor-nedir-454/)
* [tıklayınız](http://www.mechatronicturkey.com/d-c-motor-hiz-kontrol-yontemleri/)
* [tıklayınız](https://www.raspberrypi.org/blog/tag/tutorials/)
* [tıklayınız](https://docs.oracle.com/javase/tutorial/?sess=16e492aba137894101940f7f88d9f51f)
* [tıklayınız](	https://www.javatpoint.com/socket-programmings)
