export const QUERY_MEETUP_CONST = `query MeetupsPage($type: MeetupType!) { 
                            meetups(first: 50, type: $type) { 
                             edges {   
                                  node { 
                                        id   
                                        description  
                                        event_date  
                                        event_url  
                                  hosts { 
                                        ...UserImageLink 
                                        } 
                                        title 
                                  }  
                             }  
                        }                      
                    }
                   fragment UserImageLink on User {  
                                                    id   
                                                    _id   
                                                    username 
                                                    ...UserImage
                                                  }
                   fragment UserImage on User { 
                                                id 
                                                post_upvote_streak 
                                               }`;