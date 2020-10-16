Đây là một archetype với 1 số config có sẵn để tạo 1 dự án Spring + JPA với H2 + swagger nhanh.
archetype này sử dụng để viết 1 số ví dụ hoặc viết 1 số demo nhanh


# Hướng dẫn build app thành archetype

- 1 : Tại thư mục gốc của dự án. Xóa 1 số file config của iDEA đi ( .idea, *.iml... )
- 2 : Chạy lệnh `mvn archetype:create-from-project -Darchetype.properties=archetype.properties`
- 3 : Vào floder `target/generated-sources/archetype`
- 4 : Chạy lệnh `mvn clean install`


# Info archetype
 Field | Value
 --- | --- 
 GroupId | vn.com.viettel.vds.archclient
 ArtifactId | arch-client
 Version | 	1.0.1
 ## Sử dụng:
Việc sử dụng tùy thuộc vào IDE đang sử dụng.

- NetBeans: 
	
	- Sau khi thực hiện xong bước Cài đặt. Khởi động netbeans. đợi netbean index lại toàn bộ repository
	- Chọn New Project/Maven/Project from Archtype
	- Chọn tên Archtype tương ứng. 
	- Điền thông tin và next đến cuối

- IntelliJ:

	- Cài đặt và cấu hình plug-in Maven Archtype Catalogs và làm theo hướng dẫn tại đây [tại đây](https://plugins.jetbrains.com/plugin/7965-maven-archetype-catalogs)
	- Chon repo maven catalog local hoặc trên server `http://nexus.digital.vn/repository/maven-releases/archetype-catalog.xml`
	- Tạo project Maven, click vào `create from archtype`.
	- Chọn archtype tương ứng.
	- Điền thông tin, next đến cuối

- Không dùng IDE:

```bash
mvn archetype:generate                                  \
  -DarchetypeGroupId=<archetype-groupId>                \
  -DarchetypeArtifactId=<archetype-artifactId>          \
  -DarchetypeVersion=<archetype-version>                \
  -DgroupId=<my.groupid>                                \
  -DartifactId=<my-artifactId>
```
