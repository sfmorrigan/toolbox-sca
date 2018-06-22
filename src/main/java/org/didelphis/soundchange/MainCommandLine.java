/*******************************************************************************
 * Copyright (c) 2015. Samantha Fiona McCabe
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *     http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/

package org.didelphis.soundchange;

import lombok.experimental.UtilityClass;
import org.didelphis.io.DiskFileHandler;
import org.didelphis.language.phonetic.features.FeatureType;
import org.didelphis.language.phonetic.features.IntegerFeature;

/**
 * @author Samantha Fiona McCabe
 * @date 2013-09-28
 */
@UtilityClass
public final class MainCommandLine {
	private static final double NANO = 10.0E-9;
	
	public static void main(String... args) {
		if (args.length == 0) {
			throw new IllegalArgumentException("No arguments were provided!");
		} else {
			for (String arg : args) {
				double startTime = System.nanoTime();

				DiskFileHandler handler = new DiskFileHandler("UTF-8");

				CharSequence read = handler.read(arg);

				if (read == null) {
					System.out.println("Unable to load file " + arg);
					return;
				}
				
				FeatureType<?> type = IntegerFeature.INSTANCE;

				SoundChangeScript<?> script = new StandardScript<>(arg,
						type,
						read,
						handler,
						new ErrorLogger()
				);
				script.process();


				double elapsedTime = System.nanoTime() - startTime;
				double time = elapsedTime * NANO;
				System.out.println("Finished script " + arg + " in " + time + 
						" seconds");
			}
		}
	}
}
