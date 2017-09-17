<div class="container">
		<ul class="menu">
			<li><a  onclick="backToLastStep()">返回</a></li>
			<li><a>书籍管理</a>
				<ul class="submenu">
					<li><a href="/book_corner/backBookManageListQuery">图书管理</a></li>
					<li><a href="/book_corner/insertBookPage">录入新书</a></li>
					<li><a
						>图书类别</a></li>
					<li><a
						>图书来源</a></li>
					<li><a
						>设置</a></li>
				</ul></li>
			<li class="active"><a
				href=" s2">成员管理</a>
				<ul class="submenu">
					<li><a
						>审核名单</a></li>
					<li><a
						>成员名单</a></li>
					<li><a
						>用户排行</a></li>
					<li><a href="/book_corner/bookBorrowRecord">借阅记录查询</a></li>
				</ul></li>
			<li><a
				>系统管理</a>
				<ul class="submenu">
					<li><a
						>权限管理</a></li>
				</ul></li>
			<li><a
				>当前管理员：${sessionScope.system_manager.getManagerName()!=null?sessionScope.system_manager.getManagerName():"游客"}</a></li>
			<li><a
				>退出登录</a></li>
		</ul>
	</div>
