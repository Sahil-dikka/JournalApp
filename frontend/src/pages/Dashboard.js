import JournalCard from "../components/JournalCard";
import Navbar from "../components/Navbar";
import { useNavigate } from "react-router-dom";
import useGet from "../Hooks/GetDetails";
import ApiRoutes from "../ApiRoutes/ApiRoutes";

export default function Dashboard() {
    const navigate = useNavigate();

    const {data: journalData, isLoading: journalLoading, refetch} = useGet(ApiRoutes.GET.GET_JOURNALS);
    console.log("journal data", journalData);

    
  return (
    <div>
      <Navbar/>

        <div className="d-flex justify-content-between align-items-center m-4">
            <h2 className="text-center mt-2 ">Your Journals</h2>
            <button className="btn btn-success btn-outline-white" onClick={() => {navigate("/journal/new");}}>Create New Journal</button>
        </div>
      <div>
        {/* Map through user's journals and display them here */}
        <div className="d-flex flex-wrap justify-content-center">
         {journalData && journalData?.map((journal)=>(
          <JournalCard key={journal?.id} title={journal?.title} content={journal?.content} id={journal?.id} /> 
         ))}
        </div>
      </div>
    </div>
  );
}
