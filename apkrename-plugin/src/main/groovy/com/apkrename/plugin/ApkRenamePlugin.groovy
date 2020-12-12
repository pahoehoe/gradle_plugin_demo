package com.apkrename.plugin

import com.android.build.api.dsl.ApkExtension
import org.gradle.api.Plugin
import org.gradle.api.Project

public class ApkRenamePlugin implements Plugin<Project> {

    public void apply(Project project) {
        // 应用名称
        def projectName = project.name
        // 打包日期，格式为：年月日时分秒
        def buildTime = new Date().format("yyyyMMddHHmmss")
        // project有很多扩展信息，我们这里需要Android相关的扩展信息，com.android.build.gradle.AppExtension
        def appExtension = project.extensions.getByType(ApkExtension)
        println("1aaaaaaaaaaaaaaaaaaaaa "+appExtension.applicationVariants)
        // 遍历扩展信息里的所有变体，根据每个变体的信息组合出Apk的文件名称
//        appExtension.applicationVariants.all { variant ->
//            // 通过方法定义我们知道variant的实际类型为ApplicationVariant
//            // val applicationVariants: DomainObjectSet<ApplicationVariant> =
//            //         dslServices.domainObjectSet(ApplicationVariant::class.java)
//            def versionCode = ((ApplicationVariant) variant).versionCode
//            def versionName = ((ApplicationVariant) variant).versionName
//            def buildType = ((ApplicationVariant) variant).buildType.name
//            def flavor = ((ApplicationVariant) variant).flavorName
//            ((ApplicationVariant) variant).outputs.all { output ->
//                // 变体输出的除了apk还有其他文件，这里我们只修改apk的文件名
//                def outputFile = ((BaseVariantOutput) output).outputFile
//                if (outputFile != null && outputFile.name.endsWith(".apk")) {
//                    ((ApkVariantOutput) output).outputFileName = "$projectName-$versionCode-$versionName-$buildType-$flavor-${buildTime}.apk"
//                }
//            }
//        }
    }

}