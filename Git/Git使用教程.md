# Git教程

## git仓库

- 初始化版本库

  -git init

- 添加文件到版本库

  -git add

  -git commit

- 查看仓库状态

  -git status

## Git工作流

![](https://github.com/xuxueli982/Learning-notes/blob/master/Git/images/QQ%E5%9B%BE%E7%89%8720190331185409.png)

第一天产品经理需求完成bash_demo

- git status

  > 查看当前状态

![](F:\Markdown\Git\images\QQ截图20190331175440.png)

- git add bash_deom.txt

  > 将文件添加到暂存区中

![](F:\Markdown\Git\images\QQ截图20190331175948.png)

- git commit -m "first commit "

  > 将暂存中的文件存到本地仓库

![](F:\Markdown\Git\images\QQ截图20190331182156.png)

产品经理临时变更需求

![](F:\Markdown\Git\images\QQ截图20190331182438.png)

- git add bash_demo.txt

> 将修改提交到暂存区

![](F:\Markdown\Git\images\QQ截图20190331182705.png)

需求不需要改了，将暂存中的文件回滚到工作区

- git reset HEAD bash_demo.txt

![](F:\Markdown\Git\images\QQ截图20190331183141.png)

清空工作区

- git checkout -- bash_demo.txt

![](F:\Markdown\Git\images\QQ截图20190331183419.png)

第二天程序开发

![](F:\Markdown\Git\images\QQ截图20190331183610.png)

第二天需求提交

![](F:\Markdown\Git\images\QQ截图20190331183755.png)

产品经理说第二次需求不用做回滚到第一次

- git log

> 拿出要回滚的commit号

- git reset --hard 8a3bd98f254eb0302a68051bc4bab2c3585958a5

![](F:\Markdown\Git\images\QQ截图20190331184310.png)

不需要做项目了，清除干净本地仓库

- git rm bash_demo.txt

![](F:\Markdown\Git\images\QQ截图20190331184723.png)

> 本地清空了这时，但是暂存区和仓库还没有清空

- git commit -m "delete bash_demo"

![](F:\Markdown\Git\images\QQ截图20190331185050.png)

Git 工作流

## 添加远程仓库

- git remote add origin git@github.com:xuxuelidemo/demo4.git
- git pull origin master <font color=red>--allow-unrelated-histories</font>
- git push -u origin master

```
//新建一个README文档
echo "# xuxueli_code" >> README.md
//初始化仓库
git init
//把workspace的文件添加到暂存区（索引）
git add README.md
//添加到master分支
git commit -m "first commit"
//将本地仓库与远程的GitHub仓库关联起来（origin是指远程仓库）
git remote add origin https://github.com/xuxueli982/xuxueli_code.git
//将仓库推到远程仓库（-u是指将本地的master分支与远程关联，第二次就不需要打了）
git push -u origin master
```

## 克隆仓库

- git clone git@git hub.com:xuxuelidemo/demo4.git

  > 克隆一份远程仓库到本地

  ```echo "clone demo">>clon.txt```

  > echo:是输出
  >
  > ```>>```:追加















