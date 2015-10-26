var minST = new Date();
minST.setHours(8);
minST.setMinutes(0);

var minET = new Date();
minET.setHours(20);
minET.setMinutes(0);

var cvosST = new Date();
cvosST.setHours(14);
cvosST.setMinutes(15);

var cvosET = new Date();
cvosET.setHours(15);
cvosET.setMinutes(0);

var kanbanST = new Date();
kanbanST.setHours(15);
kanbanST.setMinutes(0);

var kanbanET = new Date();
kanbanET.setHours(16);
kanbanET.setMinutes(0);

var myo2ST = new Date();
myo2ST.setHours(14);
myo2ST.setMinutes(15);

var myo2ET = new Date();
myo2ET.setHours(15);
myo2ET.setMinutes(0);

var otherST = new Date();
otherST.setHours(15);
otherST.setMinutes(0);

var otherET = new Date();
otherET.setHours(17);
otherET.setMinutes(0);

var currentBookings = {
	pinnacle : [ {
		teamName : 'Available',
		startTime : minST,
		endTime : minET,
		bookedBy : '',
		bookedWhen : ''
	}, {
		teamName : 'CVoS',
		startTime : cvosST,
		endTime : cvosET,
		bookedBy : 'Anand Muley',
		bookedWhen : minST
	}, {
		teamName : 'KANBAN',
		startTime : kanbanST,
		endTime : kanbanET,
		bookedBy : 'Raj Malhotra',
		bookedWhen : minST
	} ],
	other : [ {
		teamName : 'MYO2',
		startTime : myo2ST,
		endTime : myo2ET,
		bookedBy : 'Rajat Bhatia',
		bookedWhen : minST
	}, {
		teamName : 'Available',
		startTime : otherST,
		endTime : otherET,
		bookedBy : '',
		bookedWhen : ''
	} ]
};

var teams = {
	teamNames : [ "CVoS", "ECOM" ],
	members : [ {
		name : "Abhay Suri",
		teamName : "CVoS"
	}, {
		name : "Rajat Bhatia",
		teamName : "CVoS"
	}, {
		name : "Bahubali",
		teamName : "ECOM"
	}, {
		name : "Rajnikant",
		teamName : "ECOM"
	} ]
}
