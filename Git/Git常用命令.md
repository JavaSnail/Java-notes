# Git 常用命令

## B.1 配置

Git 的配置,分为如下3个级别。

(1)config-system:修改/etc/gitconfg文件,是全局配置,只需要系统admin做一次即可。

(2)config-global:修改/home/[username]/.gitconfig文件,配置只对每一个SSH的用户可见。

(3)config-e:修改工作区的.git/config文件,配置只对当前仓库有效。

覆盖顺序为:(3)>(2)>(1).

1)修改提交者的信息。

git config--global user.name [username]

git config--global user.email [email]

2)修改Git的message 编辑器为 Vim。

git config--global core.editor vim

3)在git命令中开启颜色显示。

git config--global color.ui true

4)区分文件名大小写。

编辑每一个Git项目下的.git/config文件,设置 core.ignorecase为false,或者

git mv oldFileName newFileName

5)兼容不同平台的换行符。

Windows:git config --global core.autocrlf true

Mac: git contig --global core.autocrlf input

可以关闭关于换行符的提示:git config --global core.safecrLtfalse。

6)如果使用HTTP clone 遇到提交大小限制,请使用以下命令提高限制值。

git config --global http.postBuffer 524288000(bytes)

7)此外,也可以使用以下命令进入编辑页面做相应修改。

git config -e --global

8)配置Git常用命令的alias.

sudo git config --system alias.st status #git st

ando qit config--system alias.ci commit #git ci

sudo git config--system alias.co checkout #git co

sudo git config--system alias.br branch #git br

这里-system 参数将修改/etc/gitconfig文件,是全局配置,只需要admin做一次即可。

9)进入工作根目录,运行git config -e,会修改工作区的-git/config文件。

需要注意:Git config文件的覆盖顺序是(3)>(1)>(2).

10)显示配置列表。

git config --list

11)配置密钥。

ssh-keygen-t rsa-C xuueli982@163.com #生成密钥,把公钥复制到Git服务器上

ssh-T git@github.com #测试是否成功

\#使用ssh-agent 管理密码,避免后续需要身份验证的地方输入密码

ssh-add-K private_key_path #添加私钥到ssh-agent中,使用-K参数将密钥加入密钥链中

ssh-add -l #查看当前计算机中存储的密钥

ssh-add -d public_key_path #将对应的私钥从ssh-agent删除

## B.2 取得项目的Git 仓库

有两种取得Git项目仓库的方法.第一种是在现存的目录下，通过导入所有文

件来创建新的Git仓库；第二种是从已有的Git 仓库克隆出一个新的镜像仓库来

1)在工作目录中初始化新仓库。

要对现有的某个项目开始用Git管理,只需到此项目所在的目录,执行

git init #在当前目录新建一个Git代码库

git init [projectName]#新建一个目录并初始化为Git代码库

2)从现有仓库克隆。

git clone git://github.com/superhj1987/test.git

这会在当前目录下创建一个名为“test”的目录,其中包含一个git的目录,用于保存

下载下来的所有版本记录,然后从中取出最新版本的文件副本。

## B.3 将记录每次更新到仓库

### 1)检查当前文件状态。

git status

### 2)追踪新文件,暂存已修改文件。

使用命令 git add`[dirName][fileName1][fileName2]`开始追踪一个新文件/文件夹(包括子文件夹)。实际上只是添加文件到暂存区域,并没有提交文件:

git add . #添加当前目录的所有文件到暂存区域

git add --a #添加所有文件和目录到暂存区域

### 3)忽略未纳人版本管理的某些文件/文件夹。

一般我们总会有些文件无须纳人Git的管理,也不希望它们出现在未追踪文件列表.通常都是一些自动生成的文件,比如日志文件,编译过程中创建的临时文件等。可以创建一个名为.gitignore的文件,列出要忽略的文件模式(每一个目录下都可以单独设置.gitignore)

文件.gitignore的格式规范如下。

- 所有空行或者以注释符号#开头的行都会被Git忽略。

- 可以使用标准的glob模式匹配。匹配模式最后的反斜杠(/)说明要忽略的是目录;要忽略指定模式以外的文件或目录,可以在模式前加上惊叹号(!)取反。

-  所谓的 glob 模式是指Shell 所使用的简化了的正则表达式。星号(*)匹配零个或多个任意字符；[abc]匹配任何一个列在方括号中的字符(这个例子要么匹配一个a，要么匹配一个b,要么匹配一个c):问号(?)只匹配一个任意字符；如果在方括号中使用短画线分隔两个字符,则表示所有在这两个字符范围内的字符都可以匹配  (比如[0-9]表示匹配0~9的所有数字)。


此外,忽略未纳人版本管理的文件或文件夹的方式还有:

- 可以为自己配置一个全局的ignore 文件,位于任何版本库之外:git config -global core.excludesfile ~/.gitignoreglobal. 
- 在.git/info/exclude 文件里设置你自己本地需要排除的文件,不会影响到其他人,也不会提交到版本库中。

###  4)忽略已经在版本库里的文件/文件夹。

- 告诉 Git忽略对已经纳入版本管理的文件a的修改，Git会一直忽略此文件直到重新告诉Git 可以再次追踪此文件。 

   git update-index --assume-unchanged a 

- 告诉Git恢复追踪a。 

   git update-index--no-assume-unchanged a 。

- 查看当前被忽略的已经纳入版本库管理的文件。 

- git ls-files -v l grep -e "^[hsmrck]" 


### 5)查看已暂存和未暂存的更新、提交之间的差异。

 git status 的显示比较简单,仅仅列出了修改过的文件,如果要查看具体修改了什么地方,可以用git diff命令

 git diff #查看尚未暂存的文件更新了哪些部分

 git diff --cached[file] #查看已经暂存起来的文件和上次提交时的快照之间的差异 

git diff` [branchl][branch2]`#显示两次提交之间的差异

###  6)提交更新。

 每次准备提交前,先用git status看下,是不是都已暂存了,然后再运行提交命令git commit 提交更新: 

git commit `[filel][file2] `#会提示输入本次提交说明 

git commit -m[messag]#直接附带提交说明 

git commit--amend#修改最后一次提交 

git commit-v#提交时显示所有diff信息 

git commit --amend -m [message] #使用新commit替代上一次提交,如果代码没有任何变化,则用来改写上一次commit的提交信息 git commit--amend [filel] [file2]...#重做上一次的commit，包括指定文件的新变化。

### 7）跳过使用暂存区

git commit -a #跳过git add步骤直接commit

### 8)移除文件。

 要从Git中移除某个文件(包括暂存区域和工作目录)，就必须从已追踪文件清单中移除(确切地说,是从暂存区域移除)，然后提交。

 可以用git rm命令完成此项工作,并连带从工作目录中删除指定的文件,这样文件以后就不会出现在未追踪文件清单中:git rm `[file1][hle2]`。

如果删除之前修改过并且已经放到暂存区域,则必须使用强制删除选项-f,以防误删除文件后丢失修改的内容。

 另外一种情况是,我们想把文件从Git 仓库中删除(即从暂存区域移除),但仍然希望保留在当前工作目录中。换句话说,仅仅从追踪清单中删除。比如一些编译文件不小心纳入仓库后,要移除追踪但不删除文件,以便稍后在.gitignore 文件中补上 用 -cached 选项 即可:git rm-cached[file].后面可以列出文件或者目录的名字,也可以使用glob模式,例如 git rm log/*.log. 

###  9)移动文件。

 要在Git 中对文件改名,可以运行如下命令:

 git mv file_from file_to 

运行git mv就相当于运行了下面3条命令: 

mv README.txt README

 git rm README.txt 

git add README 

### 10)回滚文件。

git branch backup #先备份到一个新分支

 git log #找到要回滚的版本 

git reset --hard[版本号]#回滚 

## B.4 远程仓库 

远程仓库是指托管在网络上的项目仓库。 

1) 查看当前的远程仓库。

要查看当前配置有哪些远程仓库,可以用git remote命令,它会列出每个远程仓库的简短名， 在克隆完成后,至少可以看到一个名为origin 的远程仓库,也可以使用git remote- v显示对应的克隆地址。

 2)添加远程仓库。

 要添加一个新的远程仓库,可以指定一个简单的名字,以便将来引用:

 git remote add`[shortname][url] `这里的url也可以是一个本地Git项目文件夹,如git remote add local_repository/test

3)从远程同步信息。 

git fetch [remote] #下载仓库的所有变动 

git pull `[remote][branch]`#取回远程仓库的变化并合并本地分支

 4)推送数据到远程仓库。

 项目进行到一个阶段,可以将本地仓库中的数据推送到远程仓库。 命令如下: 

git push `[remote-name][branch-name] `

把本地的master分支推送到origin服务器上(克隆操作会自动使用默认的 master和 origin,并关联),可以运行下面的命令:

 git push origin master git push -u origin master//push同时设置默认追踪分支 

​	只有在所克隆的服务器上有写权限,或者同一时刻没有其他人在Push 数据,这条命令 才会执行成功。如果在Push数据前,已经有其他人推送了若干更新,那推送操作就会被驳 回。必须先把其他人的更新merge到本地才能继续。 

此外,当本地的版本落后于远程仓库,但是想要用旧版本覆盖远程版本的话,命令如下: git push --force origin master 

推送所有分支到远程仓库: 

git push [remote] --all 

5)查看远程仓库信息。 

我们可以通过命令git remote show [remote-name]查看某个远程仓库的详细信息。

 6)远程仓库的删除和重命名。 

用git remote rename命令修改某个远程仓库在本地的简短名称，使用git remote rm 命令删除远程仓库。

7)检出远程仓库的某一分支。

 git checkout-b `[local.branch] [remote.branch]`

##  B.5分支的使用 

分支是在开发中经常使用的一个功能。 

git branch	#列出本地分支

 git branch -r	#列出远程分支 

git branch -a	#列出所有本地分支和远程分支 

git branch -v	 #查看各个分支最后一个提交对象的信息 

git branch --merge 	#查看已经合并到当前分支的分支 

git branch--no-merge  	#查看未合并到当前分支的分支 

git branch [branch-name]	#新建分支,但仍然停留在当前分支 

git branch `[branch] [commit]`	#新建一个分支,指向指定 commit 

git branch -m `[old branch name] [new branch_name]`	#重命名分支

 git checkout [branch-name]	#切换到分支

 git checkout -b [branch-name]	#新建并切换到该分支

 git checkout -b `[branchl] [branch2]`	#基于branch2新建branch1分支,并切换 

git branch -d [branch-name]	#删除分支 

git branch-D [branch-name]	#强制删除分支 

git merge [branch-name]	#将分支合并到当前分支 

git rebase [branch-name]	#将banch-name 分支上超前的提交,变基到当前分支

 git branch --set-upstream `[branch] [remote-branch]`	#建立现有分支和指定远程分支的追踪关系

- 删除远程分支

  git push origin --delete [branch-name] 

  git push origin:[branch-name]

  git branch -dr[remote/branch-name]

##  B.6 标签的使用 

当完成一个版本的开发,需要做发布的时候,给此版本打一个标签: 

git tag #列出现有标签

git tag [tag]	#新建标签

git tag [tag]	#新建一个tag在当前的commit

git tag [tag] [commit]	#新建一个tag在指定 commit

git tag -a [tag] -m 'tag comment'	 #新建带注释标签

git checkout -b [branch] [tag]	#新建一个分支,指向某个tag

git show [tag]	#查看tag信息

git checkout [tag]	 #切换到标签

git push [remote] [tag] #推送分支到源上

git push [remote] --tags	 #一次性推送所有分支业卖事

qit tag-d [tag] #删除标签

git push origin :refs/tags/v0.1	#删除远程标签

## B.7 日志

- 有时候需要查看版本的日志记录,以确定、追踪代码的变化等:

  git log	#显示当前分支的版本历史

  git log --stat	#显示commit历史,以及每次commit发生变更的文件,每次提交的文件增删数量

- 显示某个文件的版本历史,包括文件改名

  git log--follow [file]

  git whatchanged [file]

  git blame [file]	#显示指定文件由谁何时修改过

  git log-p[file	]#显示指定文件相关的每一次diff

  git show[commit]	#显示每次提交的元数据和内容变化

  git show --name-only [commit]	#显示某次提交发生变化的文件

  git show [commit]:[filename]	#显示某次提交某个文件的内容

  git reflog	 #显示当前分支的最近几次提交

- 下面是git log的高级用法:

  git log--oneline 	#把每一个提交压缩到一行

  git log--decorate	#显示指向这个提交的所有引用(比如说分支、标签等)

  git shortlog 	#把每个提交按作者分类,显示提交信息第1行。这样可以容易地看到谁做了什么

  git log--graph	#绘制一个ASCII图像来展示提交历史的分支结构

  git log-[n]#限制显示的提交数量

- 按照现实日期过滤显示结果,日期可以使用多种格式,如2017-1-1,yesterda 

  git log --after[date]	#在日期之后 

  git log--before-[date] 	在日期之前 

  git log--author-[author]兼接照作者(作者的邮箱地址也算作作者的名字)

   git log--no-merges #排除外来合并提交

  git log--merges #只显示外来合并提交

   git log master.,feature	#从master分支fork到feature 分支后发生的变化

  git log--xxx.java #--告诉后面是文件名不是分支名 

  git log--grep-"xxx" #按提交信息来过滤提交

   git log [last release]HEAD --grep feature	#仅仅显示本次发布新增加的功线

   git log-S xxx(-G[regex])#根据内容(源代码)来过滤提交 

  git log--pretty=format:[string]

- 自定义输出格式,占位符:%cn指作者名字,%h指缩略标识,%cd指提价日期 

  git log [last tag]HEAD--pretty=format:%s 	#显示上次发布后的变动,每个commit占据一行 

## B.8 撤销 

在提交了错误的修改或者想撤销文件的变动时,需要以下命令:

 git checkout [file]	#恢复暂存区域的指定文件到工作区域 

git checkout [commit] [file]	#恢复某个commit的指定文件到工作区域

 git checkout	#恢复上一个commit的所有文件到工作区域

 git reset--hard	#重置暂存区域和工作区域到上一次commit 

git reset -soft	#只回退 commit,此时可以直接git commit 

git reset [commit] [file]		#重置当前分支到commit,重置暂存区域,但工作区域不变 

git reset --hard [commit] 	#重置当前分支的HEAD为指定commit,同时重置暂存区域和工作区域,与指定commit一致不变 

git reset --keep[commit]	#重置当前HEAD为指定 commit,但保持暂存区域和工作区域并且应用到当前分支 

git revert [commit]	#新建一个commit撤销指定commit,后者的所有变化都将被前者抵消, 并且应用到当前的分支

git reset HEAD^	#回退所有内容到上一个版本 

git reset HEAD^ [file]	#回退文件的版本到上一个版本

 git reset --soft HEAD~3	#向前回退到第3个版本 

git clean -f -d	#清空未进入暂存区域的改动 

##  B.9 选则某些commit操作

git cherry-pick命令可以选择某一个分支中的一个或几个commit 来进行操作。例如, 我们有一个稳定版本的分支master,另外还有一个开发版本的分支dev,我们不能直接合并两个分支, 这样会导致稳定版本混乱,但是又想将dey中的一个功能添加到 master中, 这时就可以使用 cherry-pick。

git cherry-pick [commit id]

##  B.10 解决冲突

 在rebase或者merge时,有时候会产生 conflict,如果无法auto merge,那么一般有两种处理方式。

- 手动修改冲突的文件:修改完成后,使用git add,git commit或者git rebase- continue 等后续操作即可。

-  使用任意一方的文件作为最新文件。

   git checkout--ours xx

   git checkout--theirs xx 

## B.11 Submodule

当你的工程的部分文件是另一个Git库时,可以使用submodule(现在subtree 已经替 代了submodule)

1)添加。 

为当前工程添加 submodule,命令如下: 

git submodule add 仓库地址路径 

2)删除。

 首先,要在“gitmodules”文件中删除相应配置信息。然后,执行 git rm-cached命令将子模块所在的文件从Git中删除。

 3)下载的工程带有submodule。

 当使用git clone下来的工程中带有submodule时,初始的时候,submodule的内容并不会自动下载,此时需要执行如下命令: 

git submodule update --init --recursive  

## B.12 Subtree

 1)第一次添加子目录,建立与Git项目的关联。

 git remote add-f [子仓库名] [子仓库地址]	#-f是在添加远程仓库之后,立即执行fetch。

 git subtree add --prefix=[子目录名] [子仓库名] [分支] -squash	#-squash是把subtree的改动合并成一次commit,这样就不用拉取子项目完整的历史记录。-prefix之后的=等号也可以用空格。 

2)从远程仓库更新子目录。

 git fetch [远程仓库名] [分支] 

git subtree pulls--prefix=[子目录名] [远程分支] [分支] --squash

3)从子目录Push 到远程仓库(确认你有写权限)。

 git subtree push--prefix=[子目录名] [远程分支名] 分支 

##  B.13 其他 

 git help	#获取命令的帮助信息

 git archive	#生成一个可供发布的压缩包 

git rev-list --max-count=1 HEAD	 #查看当前分支的最新rev

 git filter-branch-f --env-filter "GIT AUTHOR NAME='XX';:GIT AUTHOR_ EMAIL='XX'; GIT COMMITTER NAME='XX';GIT COMMITTER EMAIL='newemail';"HEAD	#可以修改历史记录中的作者名字和邮箱 

git filter-branch --index-filter 'git rm --cached--ignore-unmatch *.log

 删除1og文件的历史记录 

git clone http://username:password@host/project	#使用指定用户的密码克隆项目  