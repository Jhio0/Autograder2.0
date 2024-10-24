const BASE_URL = "http://localhost:3001/api"

//{ "https://app.jsundby.dev", "http://localhost:3000", "https://nopain.sunsab.com" }

const scoringMapGAD = {
  "not at all": 0,
  "several days": 1,
  "more than ½ the days": 2,
  "nearly every day": 3,
};

const scoringMapISI = {
  "none": 0,
  "mild": 1,
  "moderate": 2,
  "severe": 3,
  "very": 4,
  "very satisfied": 0,
  "satisfied": 1,
  "neutral": 2,
  "unsatisfied": 3,
  "very dissatisfied": 4,
  "not at all": 0,
  "a little": 1,
  "somewhat": 2,
  "much": 3,
  "very much": 4,
};

const scoringMapIEQ = {
  "never": 0,
  "rarely": 1,
  "sometimes": 2,
  "often": 3,
  "all the time": 4,
}

const scoringMapPCS = {
  "not at all": 0,
  "to a slight degree": 1,
  "to a moderate degree": 2,
  "to a great degree": 3,
  "all the time": 4,
};

const scoringMapTKS = {
  "strongly disagree": 1,
  "somewhat disagree": 2,
  "somewhat agree": 3,
  "strongly agree": 4,
};

const scoringMapSH = {
  "not at all": 0,
  "to a slight degree": 1,
  "to a moderate degree": 2,
  "to a great degree": 3,
  "all the time": 4,
};

const scoringMapPHQ9 = {
  "not at all": 0,
  "several days": 1,
  "more than 1/2 the days": 3,
  "nearly everyday": 4,

  "not difficult at all": 0,
  "somewhat difficult": 1,
  "very difficult": 3,
  "extremly difficult": 4,
};


const anxietyLevels = [
  { max: 4, label: "Minimal anxiety", color: "green" },
  { max: 9, label: "Mild anxiety", color: "yellow" },
  { max: 14, label: "Moderate anxiety", color: "orange" },
  { max: 60, label: "Severe anxiety", color: "red" },
];

const insomniaLevels = [
  { max: 7, label: "No clinically significant insomnia", color: "green" },
  { max: 14, label: "Sub-threshold insomnia", color: "yellow" },
  { max: 21, label: "Clinical insomnia (moderately severe)", color: "orange" },
  { max: 28, label: "Clinical insomnia (severe)", color: "red" },
];

const ieqLevels = [
  { max: 19, label: "Low", color: "green" },
  { max: 29, label: "Medium", color: "yellow" },
  { max: 60, label: "Considered clinically relevant or high", color: "red" },
];

const pcsLevels = [
  { max: 18, label: "Low", color: "green" },
  { max: 29, label: "Medium", color: "yellow" },
  { max: 60, label: "High", color: "red" },
];

const tksLevels = [
  { max: 32, label: "Low", color: "green" },
  { max: 48, label: "Medium", color: "yellow" },
  { max: 60, label: "High", color: "red" },
];

const phq9Levels = [
  { max: 5, label: "Almost always signified the absence of a depressive disorder", color: "green" },
  { max: 9, label: "Predominantly represented patients with either no depression or subthreshold", color: "yellow" },
  { max: 14, label: "a spectrum of patients", color: "orange" },
  { max: 60, label: "Usually indicated major depression", color: "red" },
  
];

const ieqBUItems = [3, 8, 9, 10, 11, 12];
const ieqSIItems = [1, 2, 4, 5, 6, 7];
const pcsRScoreItems = [8, 9, 10, 11];
const pcsMScoreItems = [6, 7, 13];
const pcsHScoreItems = [1, 2, 3, 4, 5, 12];
const tskAAItems = [1, 2, 3, 5, 6, 7, 9, 10, 11, 13, 14, 15, 17];
const tskSFItems = [4, 8, 12, 16];


const getLevel = (totalScore, formId) => {
  if (formId === '1') return anxietyLevels.find(level => totalScore <= level.max);
  if (formId === '2') return insomniaLevels.find(level => totalScore <= level.max);
  if (formId === '3') return ieqLevels.find(level => totalScore <= level.max);
  if (formId === '4') return pcsLevels.find(level => totalScore <= level.max);
  if (formId === '5') return tksLevels.find(level => totalScore <= level.max);
  if (formId === '6') return pcsLevels.find(level => totalScore <= level.max);
  if (formId === '7') return phq9Levels.find(level => totalScore <= level.max);

  return null;
};

const getScoringMap = (formId) => {
  if (formId === '1') return scoringMapGAD;
  if (formId === '2') return scoringMapISI;
  if (formId === '3') return scoringMapIEQ;
  if (formId === '4') return scoringMapPCS;
  if (formId === '5') return scoringMapTKS;
  if (formId === '6') return scoringMapSH;
  if (formId === '7') return scoringMapPHQ9;
  return null;
};

const assessment = (formId) => {
  if (formId === '1') return "Anxiety Level:";
  if (formId === '2') return "Insomnia Level:";
  if (formId === '3') return "IEQ Assessment Level:";
  if (formId === '4') return "PCS Assessment Level:";
  if (formId === '5') return "TKS Assessment Level:";
  if (formId === '6') return "SH Assessment Level:";
  if (formId === '7') return "PHQ9 Assessment Level:";
  return null;
};


export
{ 
  BASE_URL, 
  ieqBUItems,
  ieqSIItems,
  pcsRScoreItems,
  pcsMScoreItems,
  pcsHScoreItems,
  tskAAItems,
  tskSFItems,
  getLevel,
  getScoringMap,
  assessment
}

