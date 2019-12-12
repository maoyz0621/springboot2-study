1. 项目先运行springboot2-flyway, 执行SQL脚本;

2. *.mapper.master包, 操作主库, 执行update insert delete语句

3. *.mapper.slave包, 操作从库, 执行select语句