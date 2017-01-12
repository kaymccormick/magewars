node {
    def mvnHome = tool 'maven snapshot'
    stage 'checkout'
    git(url: 'ssh://jenkins@corps.heptet.us:29418/heptet/magewars.git', branch: 'italy')
    stage 'clean'
    sh "${mvnHome}/bin/mvn -B -P jenkinsHeadless clean"
    stage 'build'
    sh "${mvnHome}/bin/mvn -B -DmySkipTests=true -P jenkinsHeadless package"
    stage 'test'
    sh "${mvnHome}/bin/mvn -B -Dmaven.skip.main=true -DmySkipTests=false -P jenkinsHeadless test"
    stage 'verify'
    sh "${mvnHome}/bin/mvn -B -DmySkipTests=true -P jenkinsHeadless verify"
}
