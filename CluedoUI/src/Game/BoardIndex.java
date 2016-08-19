package Game;

/**
 * Text-based representation of the board
 *
 * @author Jeremy
 *
 */
public abstract class BoardIndex {

	public static final String boardString = "WXX|0,WXX|1,WXX|2,WXX|3,WXX|4,WXX|5,WXX|6,WXX|7,WXX|8,CMG|9,WXX|10,WXX|11,WXX|12,WXX|13,CMW|14,WXX|15,WXX|16,"
			+ "WXX|17,WXX|18,WXX|19,WXX|20,WXX|21,WXX|22,WXX|23,RKitchen   24,RKitchen   25,RKitchen   26,RKitchen   27,RKitchen  |28,SSP|29,"
			+ "H__|30,H__|31,H__|32,H__|33,RBallRoom   34,RBallRoom   35,RBallRoom   36,RBallRoom  |37,H__|38,H__|39,H__|40,WXX|41,RConservatory   42,"
			+ "RConservatory   43,RConservatory   44,RConservatory   45,RConservatory   46,RConservatory  |47,RKitchen   48,RKitchen   49,RKitchen   50,"
			+ "RKitchen   51,RKitchen   52,RKitchen  |53,H__|54,H__|55,RBallRoom   56,RBallRoom   57,RBallRoom   58,RBallRoom   59,RBallRoom   60,"
			+ "RBallRoom   61,RBallRoom   62,RBallRoom  |63,H__|64,H__|65,RConservatory   66,RConservatory   67,RConservatory   68,RConservatory   69,"
			+ "RConservatory   70,RConservatory  |71,RKitchen   72,RKitchen   73,RKitchen   74,RKitchen   75,RKitchen   76,RKitchen  |77,H__|78,H__|79,"
			+ "RBallRoom   80,RBallRoom   81,RBallRoom   82,RBallRoom   83,RBallRoom   84,RBallRoom   85,RBallRoom   86,RBallRoom  |87,H__|88,H__|89,"
			+ "RConservatory   90,RConservatory   91,RConservatory   92,RConservatory   93,RConservatory   94,RConservatory  |95,RKitchen   96,"
			+ "RKitchen   97,RKitchen   98,RKitchen   99,RKitchen   100,RKitchen  |101,H__|102,H__|103,RBallRoom   104,RBallRoom   105,RBallRoom   106,"
			+ "RBallRoom   107,RBallRoom   108,RBallRoom   109,RBallRoom   110,RBallRoom  |111,H__|112,H__|113,RConservatoryEN 114,RConservatory   115"
			+ ",RConservatory   116,RConservatory   117,RConservatory   118,RConservatory__|119,RKitchen__ 120,RKitchen   121,RKitchen   122,"
			+ "RKitchen   123,RKitchen   124,RKitchen  |125,H__|126,H__|127,RBallRoomEN 128,RBallRoom   129,RBallRoom   130,RBallRoom   131,"
			+ "RBallRoom   132,RBallRoom   133,RBallRoom   134,RBallRoomEN 135,H__|136,H__|137,H__|138,RConservatory__ 139,RConservatory__ 140,"
			+ "RConservatory__ 141,SSP|142,WXX|143,WXX|144,RKitchen__ 145,RKitchen__ 146,RKitchen__ 147,RKitchenEN 148,RKitchen__|149,H__|150,"
			+ "H__|151,RBallRoom   152,RBallRoom   153,RBallRoom   154,RBallRoom   155,RBallRoom   156,RBallRoom   157,RBallRoom   158,RBallRoom  |159,"
			+ "H__|160,H__|161,H__|162,H__|163,H__|164,H__|165,H__|166,CMP|167,H__|168,H__|169,H__|170,H__|171,H__|172,H__|173,H__|174,H__|175,"
			+ "RBallRoom__ 176,RBallRoomEN 177,RBallRoom__ 178,RBallRoom__ 179,RBallRoom__ 180,RBallRoom__ 181,RBallRoomEN 182,RBallRoom__|183,"
			+ "H__|184,H__|185,H__|186,H__|187,H__|188,H__|189,H__|190,WXX|191,WXX|192,H__|193,H__|194,H__|195,H__|196,H__|197,H__|198,H__|199,"
			+ "H__|200,H__|201,H__|202,H__|203,H__|204,H__|205,H__|206,H__|207,H__|208,H__|209,RBilliardRoom   210,RBilliardRoom   211,"
			+ "RBilliardRoom   212,RBilliardRoom   213,RBilliardRoom   214,RBilliardRoom  |215,RDiningRoom   216,RDiningRoom   217,RDiningRoom   218,"
			+ "RDiningRoom   219,RDiningRoom  |220,H__|221,H__|222,H__|223,H__|224,H__|225,H__|226,H__|227,H__|228,H__|229,H__|230,H__|231,H__|232,"
			+ "H__|233,RBilliardRoomEN 234,RBilliardRoom   235,RBilliardRoom   236,RBilliardRoom   237,RBilliardRoom   238,RBilliardRoom  |239,"
			+ "RDiningRoom   240,RDiningRoom   241,RDiningRoom   242,RDiningRoom   243,RDiningRoom   244,RDiningRoom   245,RDiningRoom   246,RDiningRoom  |247,H__|248,H__|249,"
			+ "RMiddleRoom   250,RMiddleRoom   251,RMiddleRoom   252,RMiddleRoom   253,RMiddleRoom  |254,H__|255,H__|256,H__|257,RBilliardRoom   258,"
			+ "RBilliardRoom   259,RBilliardRoom   260,RBilliardRoom   261,RBilliardRoom   262,RBilliardRoom  |263,RDiningRoom   264,RDiningRoom   265,"
			+ "RDiningRoom   266,RDiningRoom   267,RDiningRoom   268,RDiningRoom   269,RDiningRoom   270,RDiningRoom  |271,H__|272,H__|273,RMiddleRoom   274,RMiddleRoom   275,"
			+ "RMiddleRoom   276,RMiddleRoom   277,RMiddleRoom  |278,H__|279,H__|280,H__|281,RBilliardRoom   282,RBilliardRoom   283,"
			+ "RBilliardRoom   284,RBilliardRoom   285,RBilliardRoom   286,RBilliardRoom  |287,RDiningRoom   288,RDiningRoom   289,RDiningRoom   290,"
			+ "RDiningRoom   291,RDiningRoom   292,RDiningRoom   293,RDiningRoom   294,RDiningRoomEN 295,H__|296,H__|297,RMiddleRoom   298,RMiddleRoom   299,"
			+ "RMiddleRoom   300,RMiddleRoom   301,RMiddleRoom  |302,H__|303,H__|304,H__|305,RBilliardRoom__ 306,RBilliardRoom__ 307,"
			+ "RBilliardRoom__ 308,RBilliardRoom__ 309,RBilliardRoomEN 310,RBilliardRoom__|311,RDiningRoom   312,RDiningRoom   313,RDiningRoom   314,"
			+ "RDiningRoom   315,RDiningRoom   316,RDiningRoom   317,RDiningRoom   318,RDiningRoom  |319,H__|320,H__|321,RMiddleRoom   322,RMiddleRoom   323,"
			+ "RMiddleRoom   324,RMiddleRoom   325,RMiddleRoom  |326,H__|327,H__|328,H__|329,H__|330,H__|331,H__|332,H__|333,H__|334,WXX|335,"
			+ "RDiningRoom   336,RDiningRoom   337,RDiningRoom   338,RDiningRoom   339,RDiningRoom   340,RDiningRoom   341,RDiningRoom   342,RDiningRoom  |343,H__|344,H__|345,"
			+ "RMiddleRoom   346,RMiddleRoom   347,RMiddleRoom   348,RMiddleRoom   349,RMiddleRoom  |350,H__|351,H__|352,H__|353,RLibrary   354,"
			+ "RLibrary   355,RLibraryEN 356,RLibrary   357,RLibrary  |358,WXX|359,RDiningRoom__ 360,RDiningRoom__ 361,RDiningRoom__ 362,RDiningRoom__ 363,"
			+ "RDiningRoom__ 364,RDiningRoom__ 365,RDiningRoomEN 366,RDiningRoom__|367,H__|368,H__|369,RMiddleRoom   370,RMiddleRoom   371,RMiddleRoom   372,"
			+ "RMiddleRoom   373,RMiddleRoom  |374,H__|375,H__|376,RLibrary   377,RLibrary   378,RLibrary   379,RLibrary   380,RLibrary   381,"
			+ "RLibrary   382,RLibrary  |383,WXX|384,H__|385,H__|386,H__|387,H__|388,H__|389,H__|390,H__|391,H__|392,H__|393,RMiddleRoom__ 394,"
			+ "RMiddleRoom__ 395,RMiddleRoom__ 396,RMiddleRoom__ 397,RMiddleRoom__|398,H__|399,H__|400,RLibraryEN 401,RLibrary   402,RLibrary   403,"
			+ "RLibrary   404,RLibrary   405,RLibrary   406,RLibrary  |407,CCM|408,H__|409,H__|410,H__|411,H__|412,H__|413,H__|414,H__|415,H__|416,"
			+ "H__|417,H__|418,H__|419,H__|420,H__|421,H__|422,H__|423,H__|424,RLibrary__ 425,RLibrary   426,RLibrary   427,RLibrary   428,"
			+ "RLibrary   429,RLibrary   430,RLibrary  |431,WXX|432,H__|433,H__|434,H__|435,H__|436,H__|437,H__|438,H__|439,H__|440,RHall   441,"
			+ "RHall   442,RHallEN 443,RHallEN 444,RHall   445,RHall  |446,H__|447,H__|448,H__|449,RLibrary__ 450,RLibrary__ 451,RLibrary__ 452,"
			+ "RLibrary__ 453,RLibrary__ 454,WXX|455,SSP|456,RLounge   457,RLounge   458,RLounge   459,RLounge   460,RLounge   461,RLoungeEN|462,"
			+ "H__|463,H__|464,RHall   465,RHall   466,RHall   467,RHall   468,RHall   469,RHall  |470,H__|471,H__|472,H__|473,H__|474,H__|475,"
			+ "H__|476,H__|477,H__|478,CMS|479,RLounge   480,RLounge   481,RLounge   482,RLounge   483,RLounge   484,RLounge   485,RLounge  |486,"
			+ "H__|487,H__|488,RHall   489,RHall   490,RHall   491,RHall   492,RHall   493,RHallEN 494,H__|495,H__|496,H__|497,H__|498,H__|499,"
			+ "H__|500,H__|501,H__|502,WXX|503,RLounge   504,RLounge   505,RLounge   506,RLounge   507,RLounge   508,RLounge   509,RLounge  |510,"
			+ "H__|511,H__|512,RHall   513,RHall   514,RHall   515,RHall   516,RHall   517,RHall  |518,H__|519,H__|520,RStudyEN 521,RStudy   522,"
			+ "RStudy   523,RStudy   524,RStudy   525,RStudy  |526,SSP|527,RLounge   528,RLounge   529,RLounge   530,RLounge   531,RLounge   532,"
			+ "RLounge   533,RLounge  |534,H__|535,H__|536,RHall   537,RHall   538,RHall   539,RHall   540,RHall   541,RHall  |542,H__|543,H__|544,"
			+ "RStudy   545,RStudy   546,RStudy   547,RStudy   548,RStudy   549,RStudy   550,RStudy  |551,RLounge   552,RLounge   553,RLounge   554,"
			+ "RLounge   555,RLounge   556,RLounge   557,RLounge__|558,H__|559,H__|560,RHall   561,RHall   562,RHall   563,RHall   564,RHall   565,"
			+ "RHall  |566,H__|567,H__|568,RStudy__ 569,RStudy   570,RStudy   571,RStudy   572,RStudy   573,RStudy   574,RStudy  |575,RLounge__ 576,"
			+ "RLounge__ 577,RLounge__ 578,RLounge__ 579,RLounge__ 580,RLounge__|581,WXX|582,HPP|583,WXX|584,RHall__ 585,RHall__ 586,RHall__ 587,"
			+ "RHall__ 588,RHall__ 589,RHall__|590,WXX|591,H__|592,WXX|593,RStudy__ 594,RStudy__ 595,RStudy__ 596,RStudy__ 597,RStudy__ 598,"
			+ "RStudy__|599,";

	public static String entranceIndex(int index){
		if(index>=128 && index<=182)
			return "BallRoom";
		else if(index==462)
			return "Lounge";
		else if(index==114)
			return "Conservatory";
		else if(index>=443&&index<=494)
			return "Hall";
		else if(index==521)
			return "Study";
		else if(index==295 || index ==366)
			return "DiningRoomRoom";
		else if(index==148)
			return "Kitchen";
		else if(index==234 || index == 310)
			return "BilliardRoom";
		else return "Library";

	}
}
