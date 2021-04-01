jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { FtXzService } from '../service/ft-xz.service';
import { IFtXz, FtXz } from '../ft-xz.model';

import { FtXzUpdateComponent } from './ft-xz-update.component';

describe('Component Tests', () => {
  describe('FtXz Management Update Component', () => {
    let comp: FtXzUpdateComponent;
    let fixture: ComponentFixture<FtXzUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let ftXzService: FtXzService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [FtXzUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(FtXzUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(FtXzUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      ftXzService = TestBed.inject(FtXzService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should update editForm', () => {
        const ftXz: IFtXz = { id: 456 };

        activatedRoute.data = of({ ftXz });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(ftXz));
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const ftXz = { id: 123 };
        spyOn(ftXzService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ ftXz });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: ftXz }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(ftXzService.update).toHaveBeenCalledWith(ftXz);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const ftXz = new FtXz();
        spyOn(ftXzService, 'create').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ ftXz });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: ftXz }));
        saveSubject.complete();

        // THEN
        expect(ftXzService.create).toHaveBeenCalledWith(ftXz);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject();
        const ftXz = { id: 123 };
        spyOn(ftXzService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ ftXz });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(ftXzService.update).toHaveBeenCalledWith(ftXz);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });
  });
});
