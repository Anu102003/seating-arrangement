package com.example.seatingarrangement.service.impl;

import com.example.seatingarrangement.dto.*;
import com.example.seatingarrangement.entity.Allocation;
import com.example.seatingarrangement.entity.Team;
import com.example.seatingarrangement.entity.TeamInfo;
import com.example.seatingarrangement.entity.Type;
import com.example.seatingarrangement.repository.AllocationRepository;
import com.example.seatingarrangement.repository.CompanyRepository;
import com.example.seatingarrangement.repository.service.AllocationRepoService;
import com.example.seatingarrangement.repository.service.CompanyRepoService;
import com.example.seatingarrangement.repository.service.TeamRepoService;
import com.example.seatingarrangement.repository.TeamRepository;
import com.example.seatingarrangement.service.AllocationAbstract;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import java.util.*;


@Slf4j
@Service
public class BacktrackingImpl  extends AllocationAbstract {

    static TreeSet<Integer> clusters = new TreeSet<>();
    static String[][] arrangement;
    static int[][] tempLayout;
    static int[][] totalSeating;
    static boolean[][] track;
    static int lastx = -1;
    static int lasty = -1;
    static int count = 0;
    static int minSteps = 100;
    static int[][] trace;


//    @Autowired
//    CompanyRepoService companyRepositoryService;

//    @Autowired
//    private TeamRepoService teamRepositoryService;

//    @Autowired
//    private AllocationRepository allocationRepository;
    
//    @Autowired
//    private AllocationRepoService allocationRepositoryService;

//    @Autowired
//    private CompanyRepository companyRepository;
//    @Autowired
//    ModelMapper modelMapper;
    
//    @Autowired
//    private TeamRepository teamRepository;
@Autowired
    public BacktrackingImpl(TeamRepoService teamRepoService,CompanyRepoService companyRepositoryService,TeamRepository teamRepository,AllocationRepoService allocationRepoService, AllocationRepository allocationRepository,ModelMapper modelMapper) {
        super(teamRepoService,companyRepositoryService, teamRepository, allocationRepoService,allocationRepository,modelMapper);
    }


    public ResponseEntity<ResponseDto> createAllocation(TeamObjectDto teamObjectDto) throws BadRequestException {
        {
            System.out.println("inside");
            int wantedSpace = 0;
            for (TeamDto teamList : teamObjectDto.getTeamDtoList())
                wantedSpace += teamList.getTeamCount();
            GetLayoutDto getLayoutDto = companyRepoService.findByLayoutId(teamObjectDto.getLayoutId());
            System.out.println(getLayoutDto);
            int totalSpace = getLayoutDto.getAvailableSpaces();
            if (wantedSpace > totalSpace) {
                throw new BadRequestException("not sufficient Spaces");
            }
            Allocation allocation = new Allocation();
            allocation.setAllocationId(UUID.randomUUID().toString());
            List<TeamInfo> teamList = new ArrayList<>();
            String teamId;
            Team team1=teamRepoService.findTeamsByTeamInfo(teamObjectDto.getTeamDtoList(),teamObjectDto.getTeamDtoList().size());
            if(team1!=null){
                teamId=team1.getTeamId();
                Type type;
                teamList=teamRepoService.findByTeamId(teamId).get().getTeams();
                if(teamObjectDto.getPreference()!=0){
                    if(teamObjectDto.getPreference()==1) {
                        type= Type.DESC;
                    }
                    else
                        type= Type.ASC;
                    Allocation allocatedLayout=allocationRepoService.findByDefaultLayoutIdAndAllocationType(teamObjectDto.getLayoutId(),type);
                    if (allocatedLayout!=null)
                        throw new BadRequestException("already Selected");
                }
            }
            else {
                Team team=new Team();
                team.setTeamId(UUID.randomUUID().toString());
                int total = 0;
                for (TeamDto teams : teamObjectDto.getTeamDtoList()) {
                    TeamInfo teamInfo = new TeamInfo();
                    modelMapper.map(teams, teamInfo);
                    String teamCode = createTeamCode(++total);
                    teamInfo.setTeamCode(teamCode);
                    teamList.add(teamInfo);
                }
                team.setTeams(teamList);
                team.setLayoutId(teamObjectDto.getLayoutId());
                team=teamRepository.save(team);
                teamId=team.getTeamId();}
            allocation.setTeamId(teamId);
            allocation.setDefaultLayoutId(teamObjectDto.getLayoutId());
            if (teamObjectDto.getPreference() == 1) {
                allocation.setAllocationType(Type.DESC);
                teamList.sort(Comparator.comparing(TeamInfo::getTeamCount).reversed());
            } else if (teamObjectDto.getPreference() == 2){
                allocation.setAllocationType(Type.ASC);
                teamList.sort(Comparator.comparing(TeamInfo::getTeamCount));
            }
            else{
                allocation.setAllocationType(Type.RANDOM);
                teamList = new HashSet<>(teamList).stream().toList();
            }
            log.info(teamList.toString());
            int[][] defaultLayout = getLayoutDto.getLayout();
            arrangement = new String[defaultLayout.length][defaultLayout[0].length];
            tempLayout = defaultLayout;
            findArrangement(teamList);
            UserReferenceDto userReferenceDto = new UserReferenceDto();
            List<UserReferenceDto.TeamReference> teams = teamList.stream().map(a -> modelMapper.map(a, UserReferenceDto.TeamReference.class))
                    .toList();
            userReferenceDto.setTeamReferenceList(teams);
            userReferenceDto.setAllocation(arrangement);
            allocation.setAllocationLayout(arrangement);
//        for (int i = 0; i < arrangement.length; i++) {
//            for (int j = 0; j < arrangement[0].length; j++)
//                System.out.print(arrangement[i][j] + " ");
//            System.out.println();
//        }
            allocationRepository.save(allocation);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto(userReferenceDto,"allocation saved",HttpStatus.OK));
        }
    }

    private String createTeamCode(int total) {
        String[] alph = {"", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R",
                "S", "T", "U", "V", "W", "X", "Y", "Z"};
        String teamCode = "";
        if (total % 26 == 0)
            teamCode += alph[(total / 26) - 1] + "Z";
        else
            teamCode += alph[total / 26] + alph[total % 26];
        return teamCode;
    }

    private static void findArrangement(List<TeamInfo> teamList) {
        track = new boolean[tempLayout.length][tempLayout[0].length];
        totalSeating = findTotalSeating(tempLayout);
//        for (int i = 0; i < totalSeating.length; i++) {
//            for (int j = 0; j < totalSeating[0].length; j++)
//                System.out.print(totalSeating[i][j] + " ");
//            System.out.println();
//        }
        for (TeamInfo team : teamList) {
            lastx = -1;
            lasty = -1;
            int totalMembers = team.getTeamCount();
            int total = totalMembers;
            int max = (int) clusters.toArray()[clusters.size() - 1];
            count = 0;
            int c = count;
            while (total > 0) {
                findStartSeating(Math.min(total, max), team.getTeamCode());
                total = totalMembers - count;
                if (count == c) {
                    log.info("no space extend the office");
                    break;
                }
                c = count;
            }
        }
    }
    private static int[][] findTotalSeating(int[][] tempLayout) {
        clusters.clear();
        int[][] totalSeating = new int[tempLayout.length + 1][tempLayout[0].length + 1];
        for (int i = 1; i <= tempLayout.length; i++) {
            for (int j = 1; j <= tempLayout[0].length; j++) {
                if (tempLayout[i - 1][j - 1] == 0 || tempLayout[i - 1][j - 1] == -1)
                    totalSeating[i][j] = 0;
                else if (totalSeating[i][j - 1] == 0 || totalSeating[i - 1][j] == 0)
                    totalSeating[i][j] = totalSeating[i][j - 1] + totalSeating[i - 1][j] + tempLayout[i - 1][j - 1];
                else
                    totalSeating[i][j] = totalSeating[i][j - 1] + totalSeating[i - 1][j] - totalSeating[i - 1][j - 1] + tempLayout[i - 1][j - 1];
                clusters.add(totalSeating[i][j]);
            }
        }
        return totalSeating;
    }

    private static void findStartSeating(int totalMembers, String teamCode) {
        int wantedx = 0;
        int wantedy = 0;
        int minDis = 100;
        int total = totalMembers;
        if (!clusters.contains(totalMembers)) {
            for (Integer check : clusters) {
                if (check < totalMembers) {
                    total = check;
                } else
                    break;
            }
        }
        for (int i = 1; i <= tempLayout.length; i++) {
            int c = 0;
            for (int j = 1; j <= tempLayout[0].length; j++) {
                if (totalSeating[i][j] <= total && totalSeating[i][j] != 0) {
                    if (lasty == -1 && lastx == -1 && totalSeating[i][j] == total) {
                        c = 1;
                        wantedx = i;
                        wantedy = j;
                        break;
                    } else if (lasty != -1 && lastx != -1) {
                        int calculatedDis = findDistance(i, j, teamCode);
                        if (calculatedDis < minDis || (calculatedDis == minDis && ((totalSeating[i][j] == total) || totalSeating[wantedx][wantedy] < totalSeating[i][j]))) {
                            wantedx = i;
                            wantedy = j;
                            minDis = calculatedDis;
                        }
                    }
                }
            }
            if (lasty == -1 && lastx == -1 && c == 1)
                break;
        }

        lastx = wantedx;
        lasty = wantedy;
        if (totalMembers > totalSeating[wantedx][wantedy])
            totalMembers = totalSeating[wantedx][wantedy];
        markSeating(wantedx, wantedy, teamCode, totalMembers + count);
        totalSeating = findTotalSeating(tempLayout);
    }


    static int findDistance(int x, int y, String teamCode) {
        minSteps = 100;
        trace = new int[arrangement.length][arrangement[0].length];
        findSteps(lastx, lasty, x, y, 0, teamCode);
        return minSteps;
    }
    static boolean findSteps(int x, int y, int resultx, int resulty, int steps, String teamCode) {
//        log.
        if (x == resultx && y == resulty) {
            steps += 1;
            if (steps < minSteps)
                minSteps = steps;
            return false;
        }
        if ((lastx > resultx && x > lastx) || (lastx < resultx && x < lastx) || (lasty > resulty && y > lasty) || (lasty < resulty && y < lasty))
            return false;

        if (steps > minSteps)
            return false;
        if (x > 0 && y > 0 && x <= arrangement.length && y <= arrangement[0].length && trace[x - 1][y - 1] == 0) {
            trace[x - 1][y - 1] = 1;
            if ((track[x - 1][y - 1] && arrangement[x - 1][y - 1].contains(
                    teamCode)) || (!track[x - 1][y - 1] && totalSeating[x][y] == 0))
                steps -= 1;
            if (tempLayout[x - 1][y - 1] == -1)
                steps += 2;
            if (findSteps(x, y + 1, resultx, resulty, steps + 1, teamCode))
                return true;
            if (findSteps(x - 1, y, resultx, resulty, steps + 1, teamCode))
                return true;
            if (findSteps(x + 1, y, resultx, resulty, steps + 1, teamCode))
                return true;
            return findSteps(x, y - 1, resultx, resulty, steps + 1, teamCode);
        }
        return false;
    }


    private static boolean markSeating(int x, int y, String teamCode, int totalMembers) {
        if (count == totalMembers) {
            return true;
        }
        if (x > 0 && y > 0 && x <= arrangement.length && y <= arrangement[0].length && totalSeating[x][y] != 0 && !track[x - 1][y - 1] && arrangement[x - 1][y - 1] == null) {
            tempLayout[x - 1][y - 1] = 0;
            track[x - 1][y - 1] = true;
            arrangement[x - 1][y - 1] = teamCode + (++count);
            if (markSeating(x, y - 1, teamCode, totalMembers))
                return true;
            if (markSeating(x - 1, y, teamCode, totalMembers))
                return true;
            if (markSeating(x, y + 1, teamCode, totalMembers))
                return true;
            if (markSeating(x + 1, y, teamCode, totalMembers))
                return true;
            track[x - 1][y - 1] = false;
            return false;
        }
        return false;
    }

}