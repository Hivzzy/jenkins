import { useEffect, useState } from 'react'
import './App.css'
import LineChart from "./component/LineChart";

function App() {
  
  const [data, setData] = useState([]);
  const uniqueStatuses = [...new Set(data.map((item) => item.time))];
  const poor = data.map((item) => item.status == "Poor" ? item.jumlah : null).filter((item) => item !== null)
  const good = data.map((item) => item.status == "Good" ? item.jumlah : null).filter((item) => item !== null)
  const excellent = data.map((item) => item.status == "Excelent" ? item.jumlah : null).filter((item) => item !== null)
  const [userData, setUserData] = useState({
    labels: uniqueStatuses.map((item) => item),
    datasets :[{
      label:"status",
      data: data.map((item) => item.status),
    }]
  });

 
  useEffect(() => {
    const fetchData = async () => {
      try {
        const response = await fetch('http://localhost:8080/summaries/getAll');
        if (!response.ok) {
          throw new Error(`Error fetching data: ${response.status}`);
        }
        const fetchedData = await response.json();
        console.log(fetchedData);
        setData(fetchedData);
      } catch (error) {
        setError(error);
      }
    };
    fetchData();
  }, []);

  useEffect(() => {
    setUserData({
      labels: uniqueStatuses.map((item) => item),
      datasets :[{
        label:"Poor",
        data: poor,
      },
      {
        label:"Good",
        data: good,
      },
      {
        label:"Excellent",
        data: excellent,
      }
    ]
  })
  console.log("uniq",poor)
  console.log("data",good)
},[data])


  return (
    <>
    <div style={{ display: 'flex', justifyContent: 'center', flexDirection:"row", maxWidth:"100vw" }}>
      <div className='header'>
        <div style={{textAlign:"center", marginTop:"15px", fontSize:"20px"}}>
          Dashboard Info Lokomotif
        </div>
      </div>
      </div>
       <div style={{ display: 'flex', justifyContent: 'center', flexDirection:"row", maxWidth:"90vw" }}>
      <div style={{ width:"80vw", display: 'flex', justifyContent: 'center', flex:1 }} >
        <LineChart chartData={userData} />
      </div>
    </div>
    </>
  )
}

export default App
