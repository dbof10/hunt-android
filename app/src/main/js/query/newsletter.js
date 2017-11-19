export const QUERY_NEWS_LETTER_CONST = 	`query NewslettersPage($cursor: String, $filter: NewslettersFilter!) {
    newsletters(first: 20, after: $cursor, filter: $filter) {     
    edges {      
        node {        
            id        
        ...NewsletterItem      
                
        }          
    }    
        pageInfo {      
           endCursor      
          hasNextPage          
                 }      
         }
    }
    fragment NewsletterItem on Newsletter {  
            id  
            date  
            subject  
            primary_section {    
                            image_uuid    
                            layout      
            }  
    }`;
