import JournalCard from "../components/JournalCard";
import Navbar from "../components/Navbar";
import { useNavigate } from "react-router-dom";
import useGet from "../Hooks/GetDetails";
import ApiRoutes from "../ApiRoutes/ApiRoutes";
import ModalScreen from "../components/common/ModalScreen";
import { useState,useEffect } from "react";
import { set } from "react-hook-form";
import useDelete from "../Hooks/DeleteDetails";
import { toast } from "react-toastify";


export default function Dashboard() {
  const navigate = useNavigate();
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [selectedJournal, setSelectedJournal] = useState(null);


  const {
    data: journalData,
    refetch: FetchJournals,
    } = useGet(ApiRoutes.GET.GET_JOURNALS , {enabled: true});

    const deleteEndpoint = ApiRoutes.DELETE.DELETE_JOURNAL;
    const {mutate : deleteJournal} = useDelete();
  console.log("journal data", journalData);

  const handleOpenModal = (journal) => {
    console.log("JJJJJJJJJJJJJ",journal);
    setSelectedJournal(journal);
    setIsModalOpen(true);
  };

  const handleDeleteJournal = (id) => {

    deleteJournal({
      endpoint: `${deleteEndpoint}/${id}`
    }
    ,{
      onSuccess: (data) => {
        toast.success("Journal deleted successfully");
        // Optionally, you can refetch the journals list here to reflect the deletion
        // refetch();
        setIsModalOpen(false);
        FetchJournals();
      }
    }
  );
  }

  
  return (
    <div>
      <Navbar />

      <div className="d-flex justify-content-between align-items-center m-4">
        <h2 className="text-center mt-2 ">Your Journals</h2>
        <button
          className="btn btn-success btn-outline-white"
          onClick={() => {
            navigate("/journal/new");
          }}
        >
          Create New Journal
        </button>
      </div>
      <div>
        {/* Map through user's journals and display them here */}
        <div className="d-flex flex-wrap justify-content-center">
          {journalData &&
            journalData?.map((journal) => (
              <JournalCard
                key={journal?.id}
                title={journal?.title}
                content={journal?.content}
                id={journal?.id}
                onDeleteClick={() => handleOpenModal(journal)}
              />
            ))}
        </div>
      </div>


      {isModalOpen && (
      <ModalScreen
        title="Delete Journal"
        content={`Are you sure you want to delete "${selectedJournal?.title}"?`}

        onConfirm={() => {
          // Handle delete confirmation
          handleDeleteJournal(selectedJournal?.id);
        }}
        onCancel={() => {
          setIsModalOpen(false);
        }}
        action={"Delete"}
      />
      )
    }
    </div>
  );
}
