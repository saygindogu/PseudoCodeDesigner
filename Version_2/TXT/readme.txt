--> 7/18/13
Saygın,

leftAreaPanel eskisinin aynısı yaptım, ayrıca xPartition - yPartition falan onları ekledim. Yerleri falan
bence güzel oldu yani.

treePanel'e dokunmadım TODO bıraktım.

OptionsGUI ekledim. Ama yerine bir bak yani package'ını değiştirmek isteyebilirsin? Benim için önemli değil yani :D
Görünümü şimdilik kötü ama süper olacak gibime geliyor :D Gerçi 4 tane class eklemem gerekecek gibi duruyor.

Ayrıca gene büyüyor falan.. :/

Burada'yı da silebilirsin bence de bu daha güzel.
-------------------------------------------
-->7/14/13
Ey Bartu,

id olayını hallettim, ileride problemler çıkacak ama, onu çıktığı yerde çözmeye karar verdim :D
Decision ve Repetition hazır.

Decision üzerinde bayağı düşündüm ve en son şuna karar verdim, bu bildiğimiz step gibi duracak orada,
daha sonra tree oluşturulurken node içinde Decision var ise o node a otomatik olarak iki tane çocuk eklenecek
biri if biri else kısmı, daha sonra da bu node a bir çocuk eklenmek istenirse ( drag drop ile) bunu direk
if'in çocuğu gibi ekleyeceğiz böylece saçma sapan bir iş olmayacak.

Şu editorPane de birkaç satır görüntüleme problemini çözdüm ( Step class'ında getHTMLStatement'i değiştirerek)

görünümle ilgili hiçbir şeye dokunmadım, senin de geçtiğimiz hafta bir sürü değişiklik yapmış olduğunu düşünerek.

benim değiştirdiğim classların listesi şu:

PCDTransferHandler
Project
PseudoCodeDesigner ( belki değiştirmişimdir emin olamadım yine de yazdım )
ConditionalStep
DecisionStep
PCDTreeNode
PseudoCodeStepTree
RepetitionStep
Step
StepRendererPanel( burda sadece EditorPane initilize edilrken getHTMLVariablesHighlighted falan kısmı değişti 2 satır )

şu bir node seçince tüm çocukları seçmesi olayına da bir baktım ama beceremedim onu, bir yerde yanlış yapıyorum
ama nerde yapıyorum onu anlayamadım o yüzden bıraktım onu sonraya :D istersen bir bak PseudoCodeStepTree içindeki
SelectionListener'a ama senin yapacağın çok iş vardı yani onlar biterse bak yoksa ben dönünce hallederim onu  :D

-------------------------------------------
--> 7/5/13
Abi ben şu kutu görünümünden nefret ettim artık :D yani çok uğraştım ve uğraştıkça daha çok uğraştırıyor ve
sağlıklı bir görünüm ortaya çıkarmak sandığımızdan çok çok daha zormuş yani :D Swing e saygı duydum resmen.
Şimdi o görünüm inadımdan vazgeçip güzel ve anlaşılır göstermeye odaklandığımda şöyle yaptım:

tüm kutuların arkaları tree nin sonuna kadar boyanıyort, tek ve çift satırlar farklı renkte boyanıyor böylece 
ayırt etmek kolaylaşıyor. anne-oğul ilişkisi de indent sayesinde belirgin hale geliyor ( diğer denediğim
tüm modellerde çok karışıyordu beceremiyordum en temizi bu :D)

seçili kutunun arka planı kırmızı oluyor. Arka plan dışında da panel ile istediğimiz şekilde oynayabiliriz :D

şimdi sana iki seçenek sundum, biri Burada adlı klasörün içindeki UI, biri de şu an kullanılan UI, ikisinin
aman aman farkı yok, şu an kullandığımız üzerine daha çok uğraştığım için o daha iyi görünüyor bence ama :D

edit kısmında hala sorunlar var onunla ilgileneceğim :D
--

-->7/3/13

Ya ben uğraştım paneller güzel görünsün falan diye ama hiç olmadı, yani şimdi o istediğimiz gibi yapabiliriz 
kısmı yalan oldu çünkü işler karışıyor o hale getirmeye çalışınca, yani altta da aynısının kopyası görünüyor
ki bunu istemeyiz, bir yandan da alttakinin görünmesini engellersem de bu sefer onun editör üne ulaşmamız mümkün
olmuyor bu JTree nin verdiği hale göre ki bunu da istemeyiz, yani o iş yaş :D

Bu görünümde de bazı temel problemler var çok uğraştım cidden çözmeye de kolay kolay çözülmüyor.

1) JTree, aptal, kendi boyutunda bir dünyada takılıyor adam, ne yaptıysam update edemiyorum editlerken
David de bu sorundan muzdaripti, bir çözüm bulmuş onu inceledim hatta uygulamayı denedim ama o da tutmadı bunda.
neden tutmadığını da anlamadım belki eksik bir iş yaptım, tekrar deneyeceğim uykum yokken :D

2) JTree'yi preferredSize ile halledersek, eklenen kutulara göre değişim olmuyor, ki bu da alttaki kutuyu 
görememek demek!

3) bu renderer'ler arasına 10 piksel bir boşluk bırakabileydik o zaman hoş görünürdü ama bunu da beceremedim

4)renderer'lar da kafasına göre boyutlarda takılıyorlar, o da hoş değil tabi :D

5) internette sorunların çözümüne bakarken, özellikle şu editor olayına herkes kıl olmuş, onu BasicTreeUI denen
bir class'ı override edip advanced bazı işler yaparak çözmüşler :D onu da anlayamadım yani okudum ama.. :D

http://explodingpixels.wordpress.com/2008/06/02/making-a-jtreecellrenderer-fill-the-jtree/

bu problemleri çözersek bence EditorPane olayında da büyük ilerleme göstermiş oluruz, ben hala swing component
larının nasıl çalıştığını çözebilmiş değilim abi çok karışık :D GUI zor iş :D

not: yuvarlak çerçeve yaptım, hoş oldu ama o da saçma, panelin rengi kenardan taşıyor.. :D

...

Evet şimdi açıkçası çok uğraştım :D baktım yine araştırdım falan o problemlerin birkaçını ancak çözebildim :D

en sonunda şu BasicUI'ı extend etmeye cesaret ettim, orda tüm güç sende oluyor, nitekim bir sürü hesaplama lazım :D
programı çalıştırıp bakarsan iğrenç bir görünüm çıkacak ortaya :D şimdi fikir şu: yaprak olmayan node'ların arka
planını full tek renkle dolduracağım, sonra onun çocuk sayısını da kapsayacağım, yani yine iç içe görüntü olacak :D
ha içerdeki panel kötü görünüyor dersen.. onun borderlarını kaldırırız, arka planını da direk aynı renk yaparız :D
böylece çok istediğimiz görünümü elde ederiz :D

Bayağı hallettim sayılır, renkler denemek için, bir tek alt kısımlar sorunlu kaldı onu da halletmek kolay, öğrendim
nasıl yapacaımı :D biraz matematiksel hesap gerekiyor ufak :D ama sıkıldım sonra yaparım :D kolay gelsin sana :D
----------------------------------------
-->7/2/13 17:50

VariablePanel ve VariableListPanel yaptım ve VariableListPanel'e JList ekledim. Ancak nasıl göründüğüne bakamadım
çünkü diğer kısımları hllettikten sonra bunu güzelleştiririm. Zaten JList ile oynaması çok kolaymış yani. 

---------------------------------------------
-->7/2/13 14:30
koda bir göz gezdirdim şöyle.

ufak makyajlar düzeltmeler yaptım :D

renderer a neden HTML filter lazımdı? Label için miydi? label kısmını hallettim, o yüzden sildim HTMLFilter kısmını
lazım değil yani bence :D

removeAll kısmında bence aynı efficiency de değiller çünkü her seferinde Arraylist den bir method çağırırsa
ki bir hamlede 2 kere çağıracak, doğrudan çağırmak daha hızlı olur :D tabi şöyle de düşününce 6 milyar
step i silmeyeceğiz bu yüzden de aradaki 0.1 mikro saniye çok önemli değil :D :D

ignoreCase leri sildim :D case sensitive olsun

StepRendererPanel'de mainBoxPanel koymuşsun ama kullanmayı unutmuşsun o yüzden sildim onu :D ne içindi ki o? :D

görüntüleme problemini de çözdüm :D yani David haklıymış, istediğimiz şekilde görüntüleyebileceğiz, ama bu sayede
performans açısından bize bir şey kazandırmayacak JTree, sonuçta her step için ayrı panel olacak, ama tabi
drag drop olayı kasardı bence öteki türlü :D
------------------
-->7/1/13 21:33

Bütün kodlara göz gezdirdim, TODO lara daha bir detaylı baktım. Skype görüşmesi yapmalıyız.

---------------------------------------------
-->7/1/13 17:49

drag drop olayını halletmeye çalışmaya başladım :D

---------------------------------------------
--> 6/30/13 23:27
Ya kusura bakma haftasonu zaman ayıramadım ben de :D Bi de zaten sen istanbulda olduğun için
de çok kasmadım :D

Paneller'le ilgilendim biraz ama çok değil :D

bu actionListener eklerken button larda setActionCommand gibi bir method vardı, sonra action command i alıyoruz
istersek falan, yani her buttona ayrı ayrı listener yazmaktansa tek listener kullanıp o action command i buysa
şunu yap şeklinde talimatlar verelim bence :D

abi TODO şeklinde notlar burakıyorum, biliyorum biraz zor olacak ama tüm kodlara şöyle bir göz atabilirsen sevinirim :D

o TODO'lardan biri cidden önemli :D ya biz adam edit ederken variable'ların highlight olmasını istiyor muyuz?
Bu önemli çünkü highlight edince de problem oluyor eski programı açıp bakarsan variable kelimesini değiştir
mesela :D bir de highlight etmezsek büyük bir performans kazancımız olacak, o yüzden bence TextArea kullanalım :D
editor için yani :D

EditorPanel'deki buttonların alayı protected olursa daha iyi olur bence, niyetim şu çünkü şimdi bu asıl editor var ya
onda bir tane bu panelden oluşturacağım, sonra o düğmelere ordan action listener ekleyeceğim, o düğmeler'in
yapacağı işlemi Editor kısmından yapmak daha doğru gibi geliyor çünkü bana :D yani o Panel sadece view olsun,
benim yazdığım editor de controller olsun :D ( ha bir de düşündüm o işi panelde yapsak benim editor içinden
fireEditingStopped() methodunu çağırmam gerekiyor da, ( aslında bunu da konuşalım, adam done tuşuna basınca
editleme işlemi bitsin mi bitmesin mi? bence bitmesi hoş olur :D) o methodu çağırmak için bir şekilde o 
TreeCellEditor objesine erişmek gerekecek, onun da bir yolu olmayacak gibi, yani aynı şu UpperList olayı gibi olur anca)

şimdilik bu kadar :D zaten şu TODO'lara baksan, cevap versen yarın o da yeter :D programı çalıştır bir de mutlaka
küçük bir demo hazırladım :D Yani aşağı yukarı öyle bir tarzı olacak şu an, güzelse sence de böyle devam edelim,
yoksa StepRenderer in içinde getComponent gibi bir method var onun içinde TODO da bahsettiğim gibi iç içe paneller
halinde de yapabiliriz aslında onu farkettim, onla da uğraşabiliriz yani, ki uğraşırsak bence daha güzel bir
görünüm yakalayabiliriz :D

şu panellerin güzel görünmesini sağlamalıyız bir şekilde ya, o köşesini yuvarlasak bile bir şekilde, düz renk olsa
bile bayağı daha güzel görünür :D Renkler önemli tabi :D

-------------
-->6/28/13 23:48
Ey bartu bu da sana :D,
abi ctrl shift o importları hallediyor eclipse de :D

image olayını anlamadım öyle bir soru mu sormuşum, baktım da göremedim :D

Abi o subBoxes olayını nasıl çözeriz bilmiyorum ama şimdi bir tree kullandığımız için ve  her box bir 
parent node olduğu için onun subboxları da birer child node oluyor, yani her türlü child'lar ayrı
renderer paneli içinde görünmek zorunda olacak. Zaten JTree yapıyor o işi :D iç içe görünüm de şöyle olabilir
isterse hem iç içe görür hem de Tree görünümü olur da o da pek saçma olur :D 

abi aman dikkat, pcd paketinin içine Step class ını tekrar atıp onda değişiklikler yapmışsın, Variable,
VariableList gibi classlar da da bu olay var, ama değişiklik yapmadın sanırım, haberin olsun onlaru sildim yani :D
benim attığımı extract ederken sendekileri sil, böylece karışıklık çıkmasın bu gibi durumlarda, ben öyle yapıyorum yani :D
gmail'de yedek var en kötü :D

sana TODO olarak bir sürü mesaj bıraktım kodların arasında onlara da bir bak :D yani orda yanda gör diye koydum yoksa çoğu
ilerde şunu yapmalıyız şeklinde değil :D Mesajı okuduysan ve hangi yöntemin doğru olduğunu düşünüyosan o işlemi yap
TODO yu sil :D ya da cevap vermek için TODO koy yine, kodun hangi kısmından bahsettiğimizi burda anlatmak çok zor
çünkü :D

aib Renderer ve Editor'u hallettim, bitirdim, Paneller çirkin görünüyor gerçi, bir de bazı eksiklikleri vardı
add() demeyi unutmuşsun :D neyse onları düzeltirim belki falan :D Öyle ama ben de zeyneple olacağım, pazar günü
bakarım bu işlere.

Alfabetik sıralamada hüsran geldi :D o benim yazdığım da aynı işi yapıyormuş, kodu incelerken anladım üzüldüm :D
oraya da TODO ladım ama burda da söyliyim eğer çok istiyosak yazarım ( ya da yazarız ) şu 'A' nın değeri 32 miydi
neydi ondaden yola çıkarak.. :D eğlenceli ama zor olur bir de efficient olması laızm ki öh öh :D

Bir de yaparım dediğim ama yapmadığım bir şey var mıydı? onu da hatırlat, çünkü unuttum :D
--------------------------------------------------------------------------------------
--> 6/27/13 22:42

StepRendererPanel ve StepEditorPanel'i yapmaya çalıştım. Ancak buton ve editorPane diye ayırınca subBoxes kaldı.
Bu nedenle bir tane daha class yazılıp bunların düzenini ve subBoxes olaylarını halledebilir.

Image'leri sonra yaparız şimdi kafamıza takmayalım öyle işleri.

Packageleri gördüm ama GUI ye artık oha derdim cidden :D

O Tree'lere falan hiç bakamadığım için yazamadım biz de İstanbul'a gideceğimiz için çok bakamadım zaten bilgisayara.

Bu ctrl + shift + o neyin kısaltması? :D 

Bu arada her statementBox bir statementBoxList tuttuğu için program artık kafayı yiyecekmiş yani :D save sorunu hep
bunlar yüzündendir abi ve buna da %90 eminim yani..

Pazartesi sabahı gelirim. Görüşürük.

-----------------------------------------------------
--> 6/26/13 22:00 - 23:57
Önce Sorulara cevap vererek başlıyorum: Ya ben birkaç kod inceledim falan android örnekleri falan gördüm derken
 adamlar static final variable ları hep public yapmışlar, yapmalarındaki amaç da çok mantıklı geldi 
 şöyle bir düşününce, adam bizim class ı extend ettiğinde onu kullansın doğrudan diye, final olduğu için 
 içini değiştiremiyor o yüzden kafa rahat. örnek olarak hemen tanıyacağın 
 frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE) var ya mesela o EXIT.. bir final int, ve public. 
 Public olmasa öle kullanamzsın :D O yüzden public yaptım :D
 
 if ( stepID[ stepID.length - 1 ] > 0 || stepID[ stepID.length - 1] < -3 )
		{
			nextID[ stepID.length - 1] = (byte) ( stepID[ stepID.length - 1 ] + 1 );
		}
		else if ( stepID[ stepID.length - 1 ] >= -3)
		{
			System.out.println("You are trying to add too much steps!");
			throw new TooMuchStepException( "Can't have 253 steps in a row!");
		}
		
abi burayı diyorsan o (x > 0) kısmını ilk defa orda kontrol ediyor, alttaki kısımda da else if ( x >= -3) 
dememin sebebi de spesifik olsun diye düşündüm, yoksa else den sonra bir tek  -2, -1 kalıyor 
( 0'ı yukarda kontrol ettiğimiz için).

Variable class'ında font size ile ilgili kısımları biraz değiştirdim, ki fontSize'ı  GUI kısmında methodu çağırırken 
belirleyecek şekilde ayarladım direk.

bir de dosyaları kaydederken bir uyarı geliyor ğ den dolayı, utf-8 olarak kaydet lütfen sonra benim adım 
garip görünüyor :D

bu Variable ve VariableList de düşünülmesi gereken bir sürü işler var, ben onlara TODO şeklinde 
not düştüm spesifik yerlere :D öneriler var, değiştirme konusunda çok tereddüt etme direk değiştirebilirsin 
o kısımları, bana sorcağın kısımlar olursa da  yine TODO bırak o iyiymiş :D

abi bence StepList yerine DefaultTreeModel ve DefaultMutableTreeNode kullanalım çünkü öteki türlü,
PseudoCodeTreeModel ve StepNode şeklinde iki class yazıp bunları TreeNode ve TreeModel interface'sini implement
etmemiz gerekiyor ki, öh öh yani :D Eğer eğlence olsun javamız gelişir sonra da Tree yaptık diye hava atarız diyorsan
yapalım TreeModel'i falan ben varım derim ona :D

gelecekte çok class ımızın olacağını varsayarak 
(Method olur Decision olur onların panelleri olur vs) paket sistemini biraz geliştirdim
hatta pcd.gui diye bir paket daha açacaktım da yok artık dersin diye yapmadım.
aklında bulunsun pakete sadece drag drop yapıyorsun yanda sonrasında sorun çıkan dosyaları açıp 
ctrl + shift + o yapıyorsun halloluyor :D

ben tree olayını beğendim, şu editor renderer olaylarını devreye soktuğumuzda çok şık olacak bence :D
ama bu durumda( DefaultTreeModel kullandığımız için) id'lerle nasıl ilgileniriz hiç bilmiyorum o sorun olacak :D

alfabetik sort kısmı ile ilgilenmeyi unuttum bu arada onu Variable'ın compareTo() sunda halledelim bence, 
bende bir program var hazır stringleri alfabetik sıraya sokan, (101 zamanında alıştırma olsun diye yapmıştım)
onun üstünden geçip onu uyarlarım buraya, sonra sen de bir geçersin üstünden tekrar, ama sonra :D
		
		
-----------------------------------------------------
--> 6/24/13 16:39
Ey Saygın :D

Güzel olmuş eline sağlık fakat final değişkenler neden public? Çok farketmez gerçi ama yani.. Ayrıca getNextID'de 
stepID[ stepID.length - 1 ] > 0  bunu kontrol etmeye gerek yok diğeri zaten bu işi yapıyor. Bir de comment eklemeyi
unutmuşsun en başa. Onu yaptım ama diğer ikisine dokunmadım.

Variable ve VariableList'i yazdım. Sen fontSize diye bir şey belirtmişsin Step'de onu ayarladım burası için. Ayrıca
VariableList'e düzgün bir alphabeticalSort gerekiyor TODO'ya attım.

Kontrol edebilirsin. İyi çalışmalar :D


---------------------------------------------
--> 6/24/13 16:17
Ey Bartu,

StepID kısmını komple array e çevirdim. Birkaç method ismini değiştirdim falan..

StepID ye compareTo yazdım ( eskisinden kopya çekerek ).

Step'in compareTo sunu hallettim.

equals methodlarını da yazdım hazır elim değmişken :D

sen de Variable ile ilgili olan kısımların hepsini hallet bugün yarın 
( yani TODO falan yaz en kötü, ki ne yapacağımızı bilelim sonradan :D)
---------------------------------------------
--> 6/24/2013 22:28
Ey Bartu,

gelenek sürüyor :D

StepID class ı güzel olmuş. Kafamdaki tam da buydu iyi etmişsin :D
createID( byte) methodu biraz saçma olmuş ama sanki.. Yani başka biri kullanırken banbaşka bir şey düşünebilir.
belki adını extendID( byte) şekline sokabiliriz. O createID( 1) in yaptığı işi de createID() diye 
default bir şey yapar. Öyle daha mantıklı bir kod olabilir. Bunun dışında gayet iyi.

Step'deki setID( StepID) methodunu commentten aldım, o lazım olabilir. Bir de aynı objeyi kullanmadım 
kopyaladım kullandım, id değişiklikleri başımıza bela olmasın diye.

diğer comment kısımlarını sildim, bu protected olması koşuyluyla silinebilir yalnız. 
Mesela step3.stepID.getStringId() deriz. kişisel olarak ben çok sevmiyorum o olayı ama daha pratik :D

şu nextID() kısmında da okudun sanırım, onayladıysan ben ona bir Exception ekliyorum haberin olsun.

bu ArrayList'de her şey method ile yaılıyor ya, okuması çok zor oluyor sinir oluyorum :D neyse o nextID de 
sorun son basamak -3 olduğunda mı çıkıyor yoksa -2 olduğunda mı? yani nextID olan id de son basamak -3 olabilsin,
-2 olamasın. Çünkü -2 if, -3 else i temsil edecek ya o açıdan :D ( Dediğim gibi okuyamadım bıraktım, 
sana güveniyorum orda) 

bir de methodları çağırırken this kullanmasak mı ki?

documentation için de, önce methodu yazıp bitiriyorsun, sonra üstüne "/**" yazıp enter'a basıyorsun, kolay oluyor :D



------
--> 6/24/13 15:51 PM
Ey Saygın,

ben de ID class'ı yazmaya çalıştım, kendisi bir ArrayList. Array de yapabilirdim ama bunu yaptım sıkıntı çıkar
mı bilmiyorum tabi. Ama neden bilmiyorum createID ve removeID metodları yazma ihtiyacı hissettim. Bu arada
onay vermessen diye de normal id metodlarını sadece comment e aldım. İstersen silebilirsin yani.

Variable ile ilgili konuşalım diyorum. BEnim düşüncem şu, Step'te bir liste tutalım sadece. Ama AVD ve RPC gibi
üçüncü bir görme yolu ekleyelim onda da hocanın dediği gibi işte adam değer versin variable lara ve ona göre
ayarlayalım variable ları. Yani hoca hani tahtada anlatıyordu ya işte bu değişkeni üstten alıyor bu içinde
kullanıyor gibi..

Ha ama bir başka düşüncem de var o da hani biz highlight ediyoruz ya, gene bir liste tutarız, highlight ederken
bakar bu önceden kullanmışmısın, kullanmışsan mesela onu kırmızıya boyar üstüne gelince de output variable yazar
anlatabildim mi? Bu da başka bir çözüm olabilir. BUnda tabi o üçüncü görme yolu yok. Gerçi eklene de bilir.

changeWord metodu hakkında söylediklerini anlayamadım. Hani metoda baktım orada da bir yanlış göremedim yani
mantıklı gözüküyor. Bu zaten çalışıyordu? Sorunu anlayamadım o yüzden dokunmıyım dedim ama bana koyabiliriz gibi
geldi.

getStringID'yi güzel yazmışsın, onu ID classına ekledim.

Bütün TODO'lara baktım da yazmadım çünkü dediğim gibi variable ı sen de anlamadan ya da ikimiz de onay vermeden
başlamayalım yani.

Yoo, documentation commentleri çok güzelmiş ama onun bir kısa yolu varmış gibi duruyor :D Onu söyler misin?


--------------------------
--> 6/24/13 1:15 PM
Dear Bartu,

gördüğün gibi ben başladım bayağı bir şeyler yaptım. Çoğunu eski class'tan aldım 
zaten onları ben yazmıştım, yine de bir kontrol de ettim, bazıları üzernde geliştirmeler yaptım. 
Yeni Variable ile ilgili düşüncelerini 
tam anlayamadığım için variable ile ilgili olan hiçbir methoda dokunmadım.

StatementBox'daki changeWord method u var ya, ona bir bakmanı istiyorum, yani Variable tutmanın Variable ismini 
değiştirme açısından bize kattığı bir şey yok, saçma sapan bi şey oldu o :D Ben başta öyle otomatik değişir
sanıyordum ama statement var ya String olan, onun içinden hiç çıkmıyor o kelime, her HTML yapılışında o kelimeyi
tekrar çağırıyor, ve bu changeWord methodunu son dk da eklemesek, StatementBox içinde eskiden "bok" olan bir
variabla mesela " her gün bok yaparım" cümlesinde geçiyor, onu VariableList'den değiştirince, o variable nin adı
"boks" oluyor. Ama bizim getHTMLVariablesHighlighted ın yaptığı, "her" e baktım, variable değil, "bok" a baktım
variable, "yaparım" a baktım variable değil şeklinde. Ve dolayısıyla onun içinde boks diye bir variable tutsak bile
statement String'i "her gün bok yaparım" şeklinde kalıyor, ve method ona baktığında "bok".equals("boks") yanlış
oluyor, o yüzden de altını çizmiyor. Bu durumda da saçma sapan bir iş oluyor.

Bu yukardaki olayı başka türlü nasıl çözeriz bilmiyorum o yüzden senin anlaman için uğraştım, (projeyi yaparken
anlamadım ben ne olduğunu da boşver ya demiştin o yüzden) :D

Syntax hatası mantıksal hata falan olmamasından emin olmak için de boş class lar falan yaptım.
boş methodlarda //TODO var, zaten görünüyor sol tarafta error gibi :D o TODO ların hepsine bak mutlaka :D

bir de getStringID ye de bakmanı isterim, şık oldu o :D

ha bi de en son şunu düşündüm, bence direk ID class'ı olmalı. o kadar çok method falan var ki, 
yeni bir class açılabilir onun şerefine diye düşündüm, senin fikrine göre devam ederiz.

documentation comment'leri dikkatini çekecektir, çok fazladan satır oluyor, ama başka birinin 
okuması için evrensel bir şey bu sanırım java'nın source code larında (API deki) hep bu tür commentler var, 
böyle yazmaya devam edebilirz bence :D


bu readme bence burda dursun, bunun üst kısmına yazarsın tarih falan ekleyerek böylece kayıt tutmuş oluruz.
---------------------

